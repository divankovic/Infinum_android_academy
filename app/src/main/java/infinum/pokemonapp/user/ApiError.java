package infinum.pokemonapp.user;

import com.squareup.moshi.Json;

import moe.banana.jsonapi2.Document;
import moe.banana.jsonapi2.JsonApi;
import moe.banana.jsonapi2.Resource;

/**
 * Created by bivankovic on 28.7.2017..
 */


public class ApiError{

    @Json(name = "source")
    private  String source;

    @Json(name = "detail")
    private  String detail;

    public ApiError(){}

    public ApiError(String source, String detail) {
        this.source = source;
        this.detail = detail;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getSourceString(){
        int idx = source.lastIndexOf("/");
        return source.substring(idx);
    }
}
