package influx.com.demo.retrofitapi;

import influx.com.demo.model.BasePojoClass;
import retrofit2.Call;
import retrofit2.http.GET;

public interface AppInterface {

    @GET("v2/5a1e7dd92f0000801eee2da5")
    Call<BasePojoClass> RetriveData();
}