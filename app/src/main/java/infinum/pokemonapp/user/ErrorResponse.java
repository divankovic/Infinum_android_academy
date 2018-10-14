package infinum.pokemonapp.user;

import android.support.annotation.NonNull;

import com.squareup.moshi.Json;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

import java.util.LinkedList;
import java.util.List;

import retrofit2.Response;


/**
 * Created by bivankovic on 31.7.2017..
 */
class ErrorResponse {

    @Json(name = "errors")
    List<ApiError> errors;



    public List<ApiError> getErrors() {
        return errors;
    }

    public static class ApiError {

        @Json(name = "detail")
        private String detail;

        @Json(name = "source")
        private  Pointer source;

        public String getDetail() {
            return detail;
        }
        public void setDetail(String detail){
            this.detail = detail;
        }

        public Pointer getSource() {
            return source;
        }

        public void setSource(Pointer source) {
            this.source = source;
        }
    }

    public static class Pointer{
        @Json(name = "pointer")
        private String pointer;

        public String getPointer() {
            return pointer;
        }

        public void setPointer(String pointer) {
            this.pointer = pointer;
        }

        public String getPointerString(){
            int idx = pointer.lastIndexOf("/");
            return pointer.substring(idx+1).toUpperCase();
        }
    }
    @NonNull
    public static ErrorResponse getErrorResponse(Response response) {

        Moshi moshi = new Moshi.Builder().build();
        JsonAdapter<ErrorResponse> jsonAdapter = moshi.adapter(ErrorResponse.class);

        ErrorResponse errorResponse;

        try {
            String body = response.errorBody().string();
            errorResponse = jsonAdapter.fromJson(body);
        } catch (Exception exception) {
            errorResponse = new ErrorResponse();
        }

        return errorResponse;


    }
}
