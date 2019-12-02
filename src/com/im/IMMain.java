package com.im;

import java.io.File;
import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;


interface GitHubService {
	
	@Multipart
	@POST("im-j2ee/api/agent")
	Call<String> uploadAttachment(@Part MultipartBody.Part filePart);

}

public class IMMain {

	public static void main(String[] args) throws IOException {
		Gson gson = new GsonBuilder()
		        .setLenient()
		        .create();
		
		String filepath = "/Users/dev/Applications/codes/java/eclipse_ws/ws1/im-restclient/pic412.jpg";
		
		Retrofit retrofit = new Retrofit.Builder().baseUrl("http://localhost:8080/")
				.addConverterFactory( GsonConverterFactory.create(gson)).build();
		
		GitHubService service = retrofit.create(GitHubService.class);

		File file = new File(filepath);

		MultipartBody.Part filePart = MultipartBody.Part.createFormData("file", file.getName(),
				RequestBody.create(MediaType.parse("image/*"), file));

		Call<String> call = service.uploadAttachment(filePart);
		Response<String> resp = call.execute();
		System.out.println(resp);
		System.out.println(resp.body());
	}

}
