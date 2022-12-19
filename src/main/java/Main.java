import io.javalin.Javalin;

import java.io.FileNotFoundException;

import static io.javalin.apibuilder.ApiBuilder.*;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        EmojiParser parser = new EmojiParser();
        System.out.println(parser.toString());




        var app = Javalin.create(/*config*/).start(7070);
        app.routes(() -> {
            get("/", ctx -> ctx.result("Hello World"));
        });


    }

}
