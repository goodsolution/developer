package pl.com.goodsolution.adviser.api.courseplatform;

import java.util.List;

public class MemesRequestGetResponse {
    private List<MemeGetResponse> memes;

    public MemesRequestGetResponse(List<MemeGetResponse> memes) {
        this.memes = memes;
    }

    public List<MemeGetResponse> getMemes() {
        return memes;
    }
}
