package oodjassignment;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class Customer extends User
{
    private String id;
    private static int customerCount=0;
    
    public Customer(String username, String password)
    {
        super(username, password);
        id="C"+customerCount;
        customerCount++;
    }

    @Override
    public void register() throws UsernameExistsException, IOException
    {
        BufferedReader reader = new BufferedReader(User.userFileReader());
        JsonParser parser = new JsonParser();
        JsonArray jsonArray;
        if(reader.read() != -1)
        {
            if(User.usernameExists(getUsername()))
            {
                throw new UsernameExistsException("Username Already Exists!");
            }
            jsonArray = parser.parse(reader).getAsJsonArray();
        }
        else
        {
            jsonArray = new JsonArray();
        }
        
        JsonObject jsonObj = new JsonObject();
        jsonObj.addProperty("class", this.getClass().getSimpleName());
        jsonObj.addProperty("username", getUsername());
        jsonObj.addProperty("password", getPassword());
        
        jsonArray.add(jsonObj);
        
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        PrintWriter writer = User.userFileWriter();
        writer.print(gson.toJson(jsonArray));
        writer.close();
    }
}
