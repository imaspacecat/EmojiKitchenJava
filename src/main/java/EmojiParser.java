import com.google.gson.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EmojiParser {
    private final String urlRoot = "https://www.gstatic.com/android/keyboard/emojikitchen/";
    private String emojiURL;
    private final Gson gson;
    private JsonElement json;
    private List <JsonArray> jsonArrayList;
    private List<String> supportedEmojis = Arrays.asList(
            "1fa84", // ๐ช
            "1f600", // ๐
            "1f603", // ๐
            "1f604", // ๐
            "1f601", // ๐
            "1f606", // ๐
            "1f605", // ๐
            "1f602", // ๐
            "1f923", // ๐คฃ
            "1f62d", // ๐ญ
            "1f609", // ๐
            "1f617", // ๐
            "1f619", // ๐
            "1f61a", // ๐
            "1f618", // ๐
            "1f970", // ๐ฅฐ
            "1f60d", // ๐
            "1f929", // ๐คฉ
            "1f973", // ๐ฅณ
            "1f643", // ๐
            "1f642", // ๐
            "1f972", // ๐ฅฒ
            "1f979", // ๐ฅน
            "1f60b", // ๐
            "1f61b", // ๐
            "1f61d", // ๐
            "1f61c", // ๐
            "1f92a", // ๐คช
            "1f607", // ๐
            "1f60a", // ๐
            "263a-fe0f", // โบ๏ธ
            "1f60f", // ๐
            "1f60c", // ๐
            "1f614", // ๐
            "1f611", // ๐
            "1f610", // ๐
            "1f636", // ๐ถ
            "1fae1", // ๐ซก
            "1f914", // ๐ค
            "1f92b", // ๐คซ
            "1fae2", // ๐ซข
            "1f92d", // ๐คญ
            "1f971", // ๐ฅฑ
            "1f917", // ๐ค
            "1fae3", // ๐ซฃ
            "1f631", // ๐ฑ
            "1f928", // ๐คจ
            "1f9d0", // ๐ง
            "1f612", // ๐
            "1f644", // ๐
            "1f62e-200d-1f4a8", // ๐ฎโ๐จ
            "1f624", // ๐ค
            "1f620", // ๐?
            "1f621", // ๐ก
            "1f92c", // ๐คฌ
            "1f97a", // ๐ฅบ
            "1f61f", // ๐
            "1f625", // ๐ฅ
            "1f622", // ๐ข
            "2639-fe0f", // โน๏ธ
            "1f641", // ๐
            "1fae4", // ๐ซค
            "1f615", // ๐
            "1f910", // ๐ค
            "1f630", // ๐ฐ
            "1f628", // ๐จ
            "1f627", // ๐ง
            "1f626", // ๐ฆ
            "1f62e", // ๐ฎ
            "1f62f", // ๐ฏ
            "1f632", // ๐ฒ
            "1f633", // ๐ณ
            "1f92f", // ๐คฏ
            "1f62c", // ๐ฌ
            "1f613", // ๐
            "1f61e", // ๐
            "1f616", // ๐
            "1f623", // ๐ฃ
            "1f629", // ๐ฉ
            "1f62b", // ๐ซ
            "1f635", // ๐ต
            "1fae5", // ๐ซฅ
            "1f634", // ๐ด
            "1f62a", // ๐ช
            "1f924", // ๐คค
            "1f31b", // ๐
            "1f31c", // ๐
            "1f31a", // ๐
            "1f31d", // ๐
            "1f31e", // ๐
            "1fae0", // ๐ซ?
            "1f636-200d-1f32b-fe0f", // ๐ถโ๐ซ๏ธ
            "1f974", // ๐ฅด
            "1f975", // ๐ฅต
            "1f976", // ๐ฅถ
            "1f922", // ๐คข
            "1f92e", // ๐คฎ
            "1f927", // ๐คง
            "1f912", // ๐ค
            "1f915", // ๐ค
            "1f637", // ๐ท
            "1f920", // ๐ค?
            "1f911", // ๐ค
            "1f60e", // ๐
            "1f913", // ๐ค
            "1f978", // ๐ฅธ
            "1f925", // ๐คฅ
            "1f921", // ๐คก
            "1f47b", // ๐ป
            "1f4a9", // ๐ฉ
            "1f47d", // ๐ฝ
            "1f916", // ๐ค
            "1f383", // ๐
            "1f608", // ๐
            "1f47f", // ๐ฟ
            "1f525", // ๐ฅ
            "1f4ab", // ๐ซ
            "2b50", // โญ
            "1f31f", // ๐
            "1f4a5", // ๐ฅ
            "1f4af", // ๐ฏ
            "1fae7", // ๐ซง
            "1f573-fe0f", // ๐ณ๏ธ
            "1f38a", // ๐
            "2764-fe0f", // โค๏ธ
            "1f9e1", // ๐งก
            "1f49b", // ๐
            "1f49a", // ๐
            "1f499", // ๐
            "1f49c", // ๐
            "1f90e", // ๐ค
            "1f5a4", // ๐ค
            "1f90d", // ๐ค
            "2665-fe0f", // โฅ๏ธ
            "1f498", // ๐
            "1f49d", // ๐
            "1f496", // ๐
            "1f497", // ๐
            "1f493", // ๐
            "1f49e", // ๐
            "1f495", // ๐
            "1f48c", // ๐
            "1f49f", // ๐
            "2763-fe0f", // โฃ๏ธ
            "2764-fe0f-200d-1fa79", // โค๏ธโ๐ฉน
            "1f494", // ๐
            "1f48b", // ๐
            "1f9e0", // ๐ง?
            "1fac0", // ๐ซ
            "1fac1", // ๐ซ
            "1fa78", // ๐ฉธ
            "1f9a0", // ๐ฆ?
            "1f9b7", // ๐ฆท
            "1f9b4", // ๐ฆด
            "1f480", // ๐
            "1f440", // ๐
            "1f441-fe0f", // ๐๏ธ
            "1fae6", // ๐ซฆ
            "1f44d", // ๐
            "1f937", // ๐คท
            "1f490", // ๐
            "1f339", // ๐น
            "1f33a", // ๐บ
            "1f337", // ๐ท
            "1f338", // ๐ธ
            "1f4ae", // ๐ฎ
            "1f3f5-fe0f", // ๐ต๏ธ
            "1f33b", // ๐ป
            "1f33c", // ๐ผ
            "1f341", // ๐
            "1f344", // ๐
            "1f331", // ๐ฑ
            "1f33f", // ๐ฟ
            "1f343", // ๐
            "1f340", // ๐
            "1fab4", // ๐ชด
            "1f335", // ๐ต
            "1f334", // ๐ด
            "1f333", // ๐ณ
            "1f332", // ๐ฒ
            "1fab9", // ๐ชน
            "1fab5", // ๐ชต
            "1faa8", // ๐ชจ
            "26c4", // โ
            "1f30a", // ๐
            "1f32c-fe0f", // ๐ฌ๏ธ
            "1f300", // ๐
            "1f32a-fe0f", // ๐ช๏ธ
            "1f30b", // ๐
            "1f3d6-fe0f", // ๐๏ธ
            "26c5", // โ
            "2601-fe0f", // โ๏ธ
            "1f327-fe0f", // ๐ง๏ธ
            "1f329-fe0f", // ๐ฉ๏ธ
            "1f4a7", // ๐ง
            "2602-fe0f", // โ๏ธ
            "26a1", // โก
            "1f308", // ๐
            "2604-fe0f", // โ๏ธ
            "1fa90", // ๐ช
            "1f30d", // ๐
            "1f648", // ๐
            "1f435", // ๐ต
            "1f981", // ๐ฆ
            "1f42f", // ๐ฏ
            "1f431", // ๐ฑ
            "1f436", // ๐ถ
            "1f43a", // ๐บ
            "1f43b", // ๐ป
            "1f428", // ๐จ
            "1f43c", // ๐ผ
            "1f42d", // ๐ญ
            "1f430", // ๐ฐ
            "1f98a", // ๐ฆ
            "1f99d", // ๐ฆ
            "1f437", // ๐ท
            "1f984", // ๐ฆ
            "1f422", // ๐ข
            "1f429", // ๐ฉ
            "1f410", // ๐
            "1f98c", // ๐ฆ
            "1f999", // ๐ฆ
            "1f9a5", // ๐ฆฅ
            "1f994", // ๐ฆ
            "1f987", // ๐ฆ
            "1f989", // ๐ฆ
            "1f426", // ๐ฆ
            "1f54a-fe0f", // ๐๏ธ
            "1f9a9", // ๐ฆฉ
            "1f427", // ๐ง
            "1f41f", // ๐
            "1f99e", // ๐ฆ
            "1f980", // ๐ฆ
            "1f419", // ๐
            "1fab8", // ๐ชธ
            "1f982", // ๐ฆ
            "1f577-fe0f", // ๐ท๏ธ
            "1f41a", // ๐
            "1f40c", // ๐
            "1f997", // ๐ฆ
            "1fab2", // ๐ชฒ
            "1fab3", // ๐ชณ
            "1f41d", // ๐
            "1f41e", // ๐
            "1f98b", // ๐ฆ
            "1f43e", // ๐พ
            "1f353", // ๐
            "1f352", // ๐
            "1f349", // ๐
            "1f34a", // ๐
            "1f96d", // ๐ฅญ
            "1f34d", // ๐
            "1f34c", // ๐
            "1f34b", // ๐
            "1f348", // ๐
            "1f350", // ๐
            "1f95d", // ๐ฅ
            "1fad2", // ๐ซ
            "1fad0", // ๐ซ
            "1f347", // ๐
            "1f965", // ๐ฅฅ
            "1f345", // ๐
            "1f336-fe0f", // ๐ถ๏ธ
            "1f955", // ๐ฅ
            "1f360", // ๐?
            "1f9c5", // ๐ง
            "1f33d", // ๐ฝ
            "1f966", // ๐ฅฆ
            "1f952", // ๐ฅ
            "1fad1", // ๐ซ
            "1f951", // ๐ฅ
            "1f9c4", // ๐ง
            "1f954", // ๐ฅ
            "1fad8", // ๐ซ
            "1f330", // ๐ฐ
            "1f95c", // ๐ฅ
            "1f35e", // ๐
            "1fad3", // ๐ซ
            "1f950", // ๐ฅ
            "1f96f", // ๐ฅฏ
            "1f95e", // ๐ฅ
            "1f373", // ๐ณ
            "1f9c0", // ๐ง
            "1f969", // ๐ฅฉ
            "1f356", // ๐
            "1f354", // ๐
            "1f32d", // ๐ญ
            "1f96a", // ๐ฅช
            "1f968", // ๐ฅจ
            "1f35f", // ๐
            "1fad4", // ๐ซ
            "1f32e", // ๐ฎ
            "1f32f", // ๐ฏ
            "1f959", // ๐ฅ
            "1f9c6", // ๐ง
            "1f958", // ๐ฅ
            "1f35d", // ๐
            "1f96b", // ๐ฅซ
            "1fad5", // ๐ซ
            "1f963", // ๐ฅฃ
            "1f957", // ๐ฅ
            "1f372", // ๐ฒ
            "1f35b", // ๐
            "1f35c", // ๐
            "1f363", // ๐ฃ
            "1f364", // ๐ค
            "1f35a", // ๐
            "1f371", // ๐ฑ
            "1f359", // ๐
            "1f358", // ๐
            "1f365", // ๐ฅ
            "1f960", // ๐ฅ?
            "1f367", // ๐ง
            "1f368", // ๐จ
            "1f366", // ๐ฆ
            "1f370", // ๐ฐ
            "1f382", // ๐
            "1f9c1", // ๐ง
            "1f36c", // ๐ฌ
            "1f36b", // ๐ซ
            "1f369", // ๐ฉ
            "1f36a", // ๐ช
            "1f9c2", // ๐ง
            "1f37f", // ๐ฟ
            "1f9cb", // ๐ง
            "1f37c", // ๐ผ
            "1f375", // ๐ต
            "2615", // โ
            "1f9c9", // ๐ง
            "1f379", // ๐น
            "1f37d-fe0f", // ๐ฝ๏ธ
            "1f6d1", // ๐
            "1f6a8", // ๐จ
            "1f6df", // ๐
            "2693", // โ
            "1f697", // ๐
            "1f3ce-fe0f", // ๐๏ธ
            "1f695", // ๐
            "1f68c", // ๐
            "1f682", // ๐
            "1f6f8", // ๐ธ
            "1f680", // ๐
            "2708-fe0f", // โ๏ธ
            "1f3a2", // ๐ข
            "1f3a1", // ๐ก
            "1f3aa", // ๐ช
            "1f3db-fe0f", // ๐๏ธ
            "1f3df-fe0f", // ๐๏ธ
            "1f3e0", // ๐?
            "1f3d5-fe0f", // ๐๏ธ
            "1f307", // ๐
            "1f3dd-fe0f", // ๐๏ธ
            "1f388", // ๐
            "1f380", // ๐
            "1f381", // ๐
            "1faa9", // ๐ชฉ
            "1f397-fe0f", // ๐๏ธ
            "1f947", // ๐ฅ
            "1f948", // ๐ฅ
            "1f949", // ๐ฅ
            "1f3c5", // ๐
            "1f396-fe0f", // ๐๏ธ
            "1f3c6", // ๐
            "26bd", // โฝ
            "26be", // โพ
            "1f94e", // ๐ฅ
            "1f3c0", // ๐
            "1f3d0", // ๐
            "1f3c8", // ๐
            "1f3c9", // ๐
            "1f3be", // ๐พ
            "1f945", // ๐ฅ
            "1f3f8", // ๐ธ
            "1f94d", // ๐ฅ
            "1f3cf", // ๐
            "1f3d1", // ๐
            "1f3d2", // ๐
            "1f94c", // ๐ฅ
            "1f6f7", // ๐ท
            "1f3bf", // ๐ฟ
            "26f8-fe0f", // โธ๏ธ
            "1f6fc", // ๐ผ
            "1fa70", // ๐ฉฐ
            "1f6f9", // ๐น
            "26f3", // โณ
            "1f3af", // ๐ฏ
            "1f3f9", // ๐น
            "1f94f", // ๐ฅ
            "1fa83", // ๐ช
            "1fa81", // ๐ช
            "1f93f", // ๐คฟ
            "1f3bd", // ๐ฝ
            "1f94b", // ๐ฅ
            "1f94a", // ๐ฅ
            "1f3b1", // ๐ฑ
            "1f3d3", // ๐
            "1f3b3", // ๐ณ
            "265f-fe0f", // โ๏ธ
            "1fa80", // ๐ช
            "1f9e9", // ๐งฉ
            "1f3ae", // ๐ฎ
            "1f3b2", // ๐ฒ
            "1f3b0", // ๐ฐ
            "1f3b4", // ๐ด
            "1f004", // ๐
            "1f0cf", // ๐
            "1f4f7", // ๐ท
            "1f3a8", // ๐จ
            "1f58c-fe0f", // ๐๏ธ
            "1f58d-fe0f", // ๐๏ธ
            "1faa1", // ๐ชก
            "1f9f5", // ๐งต
            "1f9f6", // ๐งถ
            "1f3b9", // ๐น
            "1f3b7", // ๐ท
            "1f3ba", // ๐บ
            "1f3b8", // ๐ธ
            "1fa95", // ๐ช
            "1f3bb", // ๐ป
            "1fa98", // ๐ช
            "1f941", // ๐ฅ
            "1fa97", // ๐ช
            "1f3a4", // ๐ค
            "1f3a7", // ๐ง
            "1f399-fe0f", // ๐๏ธ
            "1f4fa", // ๐บ
            "1f39e-fe0f", // ๐๏ธ
            "1f3ac", // ๐ฌ
            "1f3ad", // ๐ญ
            "1f39f-fe0f", // ๐๏ธ
            "1f4f1", // ๐ฑ
            "260e-fe0f", // โ๏ธ
            "1f50b", // ๐
            "1faab", // ๐ชซ
            "1f4be", // ๐พ
            "1f4bf", // ๐ฟ
            "1f4b8", // ๐ธ
            "2696-fe0f", // โ๏ธ
            "1f4a1", // ๐ก
            "1f9fc", // ๐งผ
            "1f9e6", // ๐งฆ
            "1f451", // ๐
            "1f48e", // ๐
            "1f6e0-fe0f", // ๐?๏ธ
            "26d3-fe0f", // โ๏ธ
            "1f5d1-fe0f", // ๐๏ธ
            "1f58a-fe0f", // ๐๏ธ
            "2712-fe0f", // โ๏ธ
            "270f-fe0f", // โ๏ธ
            "1f4da", // ๐
            "1f5c3-fe0f", // ๐๏ธ
            "1f4f0", // ๐ฐ
            "1f4e3", // ๐ฃ
            "1f50e", // ๐
            "1f52e", // ๐ฎ
            "1f9ff", // ๐งฟ
            "1f5dd-fe0f", // ๐๏ธ
            "1f512", // ๐
            "2648", // โ
            "2649", // โ
            "264a", // โ
            "264b", // โ
            "264c", // โ
            "264d", // โ
            "264e", // โ
            "264f", // โ
            "2650", // โ
            "2651", // โ
            "2652", // โ
            "2653", // โ
            "26ce", // โ
            "2757", // โ
            "2753", // โ
            "2049-fe0f", // โ๏ธ
            "1f198", // ๐
            "1f4f4", // ๐ด
            "1f508", // ๐
            "26a0-fe0f", // โ?๏ธ
            "267b-fe0f", // โป๏ธ
            "2705", // โ
            "1f195", // ๐
            "1f193", // ๐
            "1f199", // ๐
            "1f197", // ๐
            "1f192", // ๐
            "1f6ae", // ๐ฎ
            "262e-fe0f", // โฎ๏ธ
            "262f-fe0f", // โฏ๏ธ
            "267e-fe0f", // โพ๏ธ
            "2716-fe0f", // โ๏ธ
            "2795", // โ
            "2796", // โ
            "2797", // โ
            "27b0", // โฐ
            "27bf", // โฟ
            "3030-fe0f", // ใฐ๏ธ
            "00a9-fe0f", // ยฉ๏ธ
            "00ae-fe0f", // ยฎ๏ธ
            "2122-fe0f", // โข๏ธ
            "2660-fe0f", // โ?๏ธ
            "1f5ef-fe0f", // ๐ฏ๏ธ
            "1f4ac" // ๐ฌ
    );

    public EmojiParser() throws FileNotFoundException {
        System.out.println("parser initiated");
        gson = new GsonBuilder().setPrettyPrinting().create();
        jsonArrayList = new ArrayList<>();

        try (Reader reader = new FileReader(
                "src/main/resources/emojiData.json")) {
            // Convert JSON to JsonElement, and later to String
            json = gson.fromJson(reader, JsonElement.class);
            JsonObject  jsonObject = json.getAsJsonObject();
            for(int i = 0; i < supportedEmojis.size(); i++){
                jsonArrayList.add(jsonObject.getAsJsonArray(supportedEmojis.get(i)));
                
                System.out.println("=====================" + supportedEmojis.get(i) + "========================");
                jsonArrayList.get(i).forEach(System.out::println);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public String toString() {
        return gson.toJson(json);
    }

}
