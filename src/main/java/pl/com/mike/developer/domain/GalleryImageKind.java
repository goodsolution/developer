package pl.com.mike.developer.domain;

public enum GalleryImageKind {
    OLD("O"),
    MENU("M"),
    GALLERY_HORIZONTAL("H"),
    GALLERY_VERTICAL("V");

    private String code;

    GalleryImageKind(String code) {
        this.code = code;
    }

    public String code() {
        return code;
    }

    public static GalleryImageKind toGalleryImageKind(String code) {
        switch(code.toUpperCase()) {
            case "O":
                return GalleryImageKind.OLD;
            case "M":
                return GalleryImageKind.MENU;
            case "H":
                return GalleryImageKind.GALLERY_HORIZONTAL;
            case "V":
                return GalleryImageKind.GALLERY_VERTICAL;
        }
        throw new IllegalArgumentException("No enum defined for code: " + code);
    }
}
