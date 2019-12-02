package com.im;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
class User{
	String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "User [name=" + name + "]";
	}
	
}
class Repo {
	String login;
	String id;
	String node_id;

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNode_id() {
		return node_id;
	}

	public void setNode_id(String node_id) {
		this.node_id = node_id;
	}

	@Override
	public String toString() {
		return "Repo [login=" + login + ", id=" + id + ", node_id=" + node_id + "]";
	}

}

interface GitHubService {
	// @GET("users/{user}/repos")
	@GET("users/{user}")
	Call<Repo> listRepos(@Path("user") String user);

	@Multipart
	@POST("im-j2ee/api/agent")
	Call<String> uploadAttachment(@Part MultipartBody.Part filePart);

}

public class IMMain {

	public static void main(String[] args) throws IOException {
		// simplehttp();
		Gson gson = new GsonBuilder()
		        .setLenient()
		        .create();
		String filepath = "/Users/dev/Applications/codes/java/eclipse_ws/ws1/im-restclient/pic41.jpg";
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
		System.out.println(resp.message());
	}

	static void simplehttp() throws IOException {
		// TODO Auto-generated method stub
		Retrofit retrofit = new Retrofit.Builder().baseUrl("https://api.github.com/")
				.addConverterFactory(GsonConverterFactory.create()).build();
		GitHubService service = retrofit.create(GitHubService.class);
		Call<Repo> repos = service.listRepos("divine1");
		Response<Repo> listrepo = repos.execute();

		System.out.println(listrepo.body());

	}
}
