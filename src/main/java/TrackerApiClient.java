import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.IOException;

public class TrackerApiClient {
    private static String token;
    private static boolean clientIsClosed;
    private static CloseableHttpClient httpclient;

    public static final String PLATFORM_UPLAY = "uplay";
    public static final String PLATFORM_XBL = "xbl";
    public static final String PLATFORM_PSN = "psn";
    public static final String PLATFORM_STEAM = "steam";

    public TrackerApiClient(String apiToken) {
        token = apiToken;
        clientIsClosed = false;
        httpclient = HttpClients.createDefault();
    }

    public JSONObject getDiv2Data(String platform, String platformUserId) throws Exception {
        if (clientIsClosed) throw new Exception("Client has been closed");
        return makeRequest("division-2", platform, platformUserId);
    }

    public JSONObject getApexData(String platform, String platformUserId) throws Exception {
        if (clientIsClosed) throw new Exception("Client has been closed");
        return makeRequest("apex", platform, platformUserId);
    }

    public JSONObject getCSGOData(String platform, String platformUserId) throws Exception {
        if (clientIsClosed) throw new Exception("Client has been closed");
        return makeRequest("csgo", platform, platformUserId);
    }

    public JSONObject getOverwatchData(String platform, String platformUserId) throws Exception {
        if (clientIsClosed) throw new Exception("Client has been closed");
        return makeRequest("overwatch", platform, platformUserId);
    }

    public JSONObject getSplitgateData(String platform, String platformUserId) throws Exception {
        if (clientIsClosed) throw new Exception("Client has been closed");
        return makeRequest("splitgate", platform, platformUserId);
    }

    public JSONObject getHyperscapeData(String platform, String platformUserId) throws Exception {
        if (clientIsClosed) throw new Exception("Client has been closed");
        return makeRequest("hyper-scape", platform, platformUserId);
    }

    public void close() throws IOException {
        clientIsClosed = true;
        httpclient.close();
    }

    private JSONObject makeRequest(String game, String platform, String platformUserId) throws Exception {
        if (clientIsClosed) throw new Exception("Client has been closed");
        HttpGet get = new HttpGet("https://public-api.tracker.gg/v2/" + game + "/standard/profile/" + platform + "/" + platformUserId);
        get.setHeader("TRN-Api-Key", token);
        CloseableHttpResponse res = httpclient.execute(get);
        HttpEntity entity = res.getEntity();
        String responseString = EntityUtils.toString(entity, "UTF-8");
        return new JSONObject(responseString);
    }
}

class Test {
    public static void main(String[] args) throws Exception {
        TrackerApiClient client = new TrackerApiClient("595366f5-95f7-4f6c-92a9-fc274e36dbcc");
        System.out.println(client.getCSGOData(TrackerApiClient.PLATFORM_STEAM, "76561198008049283").toString());
    }
}