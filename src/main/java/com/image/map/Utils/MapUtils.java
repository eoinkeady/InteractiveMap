package com.image.map.Utils;


import com.image.map.domain.PostEntity;
import com.image.map.domain.PostsWrapper;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.PathNotFoundException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Service
public class MapUtils {

    @Value("${google.api.key}")
    private String apiKey;

    @Value("${google.geocode.url}")
    private String googleUrl;

    @Value("${google.json.lng.path}")
    private String googleLngPath;

    @Value("${google.json.lat.path}")
    private String googleLatPath;

    private final String USER_AGENT = "Mozilla/5.0";

    public PostsWrapper getTopRedditPosts(String url)throws Exception {
        List<PostEntity> postEntities = new ArrayList<PostEntity>();
        String json = makeCall(url);

        JSONObject o = new JSONObject(json);
        JSONArray children = o.getJSONObject("data").getJSONArray("children");

        for(int i = 0; i < children.length(); i++) {
            String postTitle = children.getJSONObject(i).getJSONObject("data").getString("title");
            String link = "https://www.reddit.com" + children.getJSONObject(i).getJSONObject("data").getString("permalink");
            String thumbnail = children.getJSONObject(i).getJSONObject("data").getString("thumbnail");
            PostEntity postEntity = new PostEntity();
            postEntity.setTitle(postTitle);
            postEntity.setLink(link);
            postEntity.setThumbnail(thumbnail);
            postEntities.add(postEntity);
        }
        postEntities = getCoordinates(postEntities);
        PostsWrapper postsWrapper = new PostsWrapper();
        postsWrapper.setPostEntityList(postEntities);
        return postsWrapper;

    }

    public List<PostEntity> getCoordinates(List<PostEntity> postEntities) {

        double lat;
        double lng;
        for(PostEntity postEntityIterator : postEntities) {
            String url = "";
            try {
                url = googleUrl.replace("{encodedAddress}", URLEncoder.encode(postEntityIterator.getTitle(), "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            url = url.replace("{apiKey}", apiKey);
            String json = makeCall(url);
            try {
                try{
                lat = JsonPath.read(json,googleLatPath);
                lng = JsonPath.read(json,googleLngPath);
                postEntityIterator.setLat(lat);
                postEntityIterator.setLng(lng);
                }catch (ClassCastException ex) {
                    System.out.println("class is fucked");
                    postEntityIterator.setLat(100.111);
                    postEntityIterator.setLng(100.111);
                }
            } catch(PathNotFoundException ex) {
                System.out.println("Path not found");
            }

        }

        for(int i = 0; i < postEntities.size(); i++) {

            System.out.println(postEntities.get(i).getTitle());
            System.out.println(postEntities.get(i).getLat());
            System.out.println(postEntities.get(i).getLng());
        }

        return postEntities;
    }

    private String makeCall(final String url) {
        HttpClient client = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet(url);
        request.addHeader("User-Agent", USER_AGENT);
        String json = "";

        HttpResponse response = null;
        try {
            response = client.execute(request);
        } catch (ClientProtocolException e1){
            e1.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Response Code : " + response.getStatusLine().getStatusCode());


        HttpEntity entity = response.getEntity();

        if(entity != null) {
            try {
                json = EntityUtils.toString(entity, StandardCharsets.UTF_8);
            } catch (ParseException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }

        return json;

    }

}
