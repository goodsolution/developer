package pl.com.mike.developer.logic.instagram;

import org.apache.commons.text.StringEscapeUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import pl.com.mike.developer.domain.InstagramData;

import java.util.LinkedList;
import java.util.List;

@Service
@Transactional
public class InstagramService {
    private static final Logger log = LoggerFactory.getLogger(InstagramService.class);
    private InstagramJdbcRepository instagramJdbcRepository;
    private RestTemplate restTemplate;

    public InstagramService(InstagramJdbcRepository instagramJdbcRepository, RestTemplate restTemplate) {
        this.instagramJdbcRepository = instagramJdbcRepository;
        this.restTemplate = restTemplate;
    }

    public void sync() {
        log.debug("sync started");
//        try {
//            String page = restTemplate.getForObject("https://www.instagram.com/masterdieta_pl/", String.class);
//            List<InstagramData> list = parse(scrap(page));
//            instagramJdbcRepository.removeAll();
//            for (InstagramData instagramData : list) {
//                instagramJdbcRepository.create(instagramData);
//            }
//        } catch (Exception ex) {
//            log.error("Problem instagram sync: " + ex);
//            ex.printStackTrace();
//        }
        log.debug("sync finished");
    }

    String scrap(final String page) {
        int startIndex = page.indexOf("window._sharedData = ") + "window._sharedData = ".length();
        String scrappedPage = page.substring(startIndex);
        int endIndex = scrappedPage.indexOf(";</script>");
        scrappedPage = scrappedPage.substring(0, endIndex);
        return scrappedPage;
    }

    List<InstagramData> parse(final String json) {
        log.trace("Parsing: " + json);
        JSONArray edges = extract(json);
        List<InstagramData> list = new LinkedList<>();
        for (Object edge : edges) {
            JSONObject e = (JSONObject) edge;
            JSONObject node = e.getJSONObject("node");
            JSONObject edgeMediaToCaption = node.getJSONObject("edge_media_to_caption");
            JSONArray innerEdges = edgeMediaToCaption.getJSONArray("edges");
            JSONObject innerEdge = innerEdges.getJSONObject(0);
            JSONObject innerNode = innerEdge.getJSONObject("node");
            String caption = StringEscapeUtils.escapeJava(innerNode.getString("text"));
            String src = node.getString("display_url");
            String shortCode = node.getString("shortcode");
            list.add(prepareData(shortCode, src, caption, caption));
        }
        return list;
    }

    InstagramData prepareData(String href, String src, String alt, String title) {
        return new InstagramData(href, src, alt, title);
    }

    private JSONArray extract(String json) {
        JSONObject object = new JSONObject(json);
        JSONObject entryData = object.getJSONObject("entry_data");
        JSONArray profilePages = entryData.getJSONArray("ProfilePage");
        JSONObject profilePage = profilePages.getJSONObject(0);
        JSONObject graphQl = profilePage.getJSONObject("graphql");
        JSONObject user = graphQl.getJSONObject("user");
        JSONObject edgeOwnerToTimelineMedia = user.getJSONObject("edge_owner_to_timeline_media");
        return edgeOwnerToTimelineMedia.getJSONArray("edges");
    }

}


