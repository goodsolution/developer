package pl.com.mike.developer.logic;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pl.com.mike.developer.domain.DictionaryData;
import pl.com.mike.developer.domain.ExcelMenuPreviewData;
import pl.com.mike.developer.domain.MenuPreviewData;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class MenuService {
    private static final Logger log = LoggerFactory.getLogger(MenuService.class);
    private MenuJdbcRepository menuJdbcRepository;
    private DictionariesService dictionariesService;
    private ProductsService productsService;

    public MenuService(MenuJdbcRepository menuJdbcRepository, DictionariesService dictionariesService, ProductsService productsService) {
        this.menuJdbcRepository = menuJdbcRepository;
        this.dictionariesService = dictionariesService;
        this.productsService = productsService;
    }

    public ExcelMenuPreviewData generateMenuPreviewExcel(final LocalDate from, final LocalDate to, final Long dietId, final Long kindId) throws IOException {
        log.debug("from: " + from + " to: " + to + " dietId: " + dietId + " kindId: " + kindId);
        if (from == null) {
            throw new IllegalArgumentException("Brak daty od");
        }
        if (to == null) {
            throw new IllegalArgumentException("Brak daty do");
        }
        if (from.isAfter(to)) {
            throw new IllegalArgumentException("Nieprawidłowy zakres dat");
        }

        List<MenuPreviewData> list = new ArrayList<>();
        LocalDate date = from;

        List<DictionaryData> groups = getDishGroupsForExcel();
        while (date.isBefore(to) || date.isEqual(to)) {
            Map<String, String> meals = new HashMap<>();
            for (DictionaryData group : groups) {
                meals.put(group.getValue(), getMenuDishNameForGroup(date, dietId, kindId, group.getId()));
            }
            list.add(new MenuPreviewData(date, meals));
            date = date.plusDays(1);
        }

        Workbook workbook = new XSSFWorkbook();

        generateExcel(list, workbook, dietId, kindId);

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            workbook.write(bos);
        } finally {
                bos.close();
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("YYYY_MM_dd_HH_mm_ss");
        return new ExcelMenuPreviewData(new ByteArrayInputStream(bos.toByteArray()), bos.size(), "Podglad_menu_" + formatter.format(LocalDateTime.now()) + ".xlsx");
    }

    private List<DictionaryData> getDishGroupsForExcel() {
        return dictionariesService.getDictionary(DictionaryType.DISH_GROUPS, Language.PL).stream().filter(o -> !"PRZ".equals(o.getCode())).collect(Collectors.toList());
    }

    private String getMenuDishNameForGroup(LocalDate date, Long dietId, Long kindId, Long groupId) {
        List<Map<String, Object>> rows = menuJdbcRepository.getMenuDishNameForGroup(date, dietId, kindId,  groupId);
        String result = "";
        int i = 0;
        for (Map row : rows) {
            result += (String) row.get("dish_name");
            if (rows.size() > 1 && i < rows.size() - 1) {
                result += "; \n";
            }
            i++;
        }

        if (result == null || "".equals(result)) {
            return "-- Brak --";
        }
        return result;
    }

    private void generateExcel(List<MenuPreviewData> list, Workbook workbook, Long dietId, Long kindId) {
        workbook.createSheet("Pogląd menu");
        workbook.getSheetAt(0).getPrintSetup().setLandscape(true);
        setExcelMargins(workbook);
        String title = getTitle(dietId, kindId);
        generateHeader(workbook, list, title);
        generateRecords(workbook, list);
    }

    private String getTitle(Long dietId, Long kindId) {
        return dictionariesService.getDictionaryValueById(dietId, DictionaryType.DIETS, Language.PL) + " " + productsService.getProductNameInLanguage(kindId, Language.PL.getCode());
    }

    private void setExcelMargins(Workbook workbook) {
        workbook.getSheetAt(0).setMargin(Sheet.LeftMargin, 0.2);
        workbook.getSheetAt(0).setMargin(Sheet.HeaderMargin, 0.2);
        workbook.getSheetAt(0).setMargin(Sheet.RightMargin, 0.2);
        workbook.getSheetAt(0).setMargin(Sheet.BottomMargin, 0.2);
        workbook.getSheetAt(0).setMargin(Sheet.TopMargin, 0.2);
    }

    private void generateHeader(Workbook workbook, List<MenuPreviewData> list, String title) {
        Sheet sheet = workbook.getSheetAt(0);
        Row header = sheet.createRow(0);
        CellStyle headerTitleStyle = workbook.createCellStyle();
        headerTitleStyle.setAlignment(HorizontalAlignment.LEFT);

        Cell headerTitleCell = header.createCell(0);
        headerTitleCell.setCellValue(title);
        headerTitleCell.setCellStyle(headerTitleStyle);

        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setBorderBottom(BorderStyle.THIN);
        headerStyle.setBorderRight(BorderStyle.THIN);
        headerStyle.setBorderTop(BorderStyle.THIN);
        headerStyle.setAlignment(HorizontalAlignment.CENTER);
        header = sheet.createRow(1);
        int i = 0;
        for (MenuPreviewData menu: list) {
            sheet.setColumnWidth(i, 4300);
            Cell headerCell = header.createCell(i++);
            headerCell.setCellValue(menu.getDate().toString());
            headerCell.setCellStyle(headerStyle);
        }

        XSSFFont font = ((XSSFWorkbook) workbook).createFont();
        font.setFontName("Arial");
        font.setFontHeightInPoints((short) 12);
        font.setBold(true);
        headerStyle.setFont(font);
    }

    private void generateRecords(Workbook workbook, List<MenuPreviewData> list) {
        CellStyle style = workbook.createCellStyle();
        style.setWrapText(true);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);

        Sheet sheet = workbook.getSheetAt(0);
        int rowNo = 2;
        Row row = sheet.createRow(rowNo++);
        List<DictionaryData> groups = getDishGroupsForExcel();
        for (DictionaryData group : groups) {
            for (int i = 0; i < list.size(); i++) {
                Cell cell = row.createCell(i);
                cell.setCellValue(list.get(i).getMeals().get(group.getValue()));
                cell.setCellStyle(style);
            }
            row = sheet.createRow(rowNo++);
        }

    }

}
