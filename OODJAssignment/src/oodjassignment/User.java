package oodjassignment;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.*;
import java.util.Objects;

public abstract class User
{
    private String username;
    private String password;
    private static int userCount=0;
    
    public User(String username, String password)
    {
        this.username = username;
        this.password = password;
        userCount++;
    }

    //getters and setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public static int getUserCount() {
        return userCount;
    }
    
    public static FileReader userFileReader()
    {
        try
        {
            return new FileReader("src/data/user.txt");
        }
        catch(FileNotFoundException e)
        {
            e.printStackTrace();
        }
        return null;
    }
    
    public static PrintWriter userFileWriter()
    {
        try
        {
            return new PrintWriter(new FileWriter(new File("src/data/user.txt")));
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        return null;
    }
    
    public static User[] getUserList()
    {
        FileReader reader = userFileReader();
        if(reader == null)
            return null;
        
        JsonParser parser = new JsonParser();
        JsonArray jsonArray = parser.parse(reader).getAsJsonArray();
        User[] users = new User[jsonArray.size()];
        
        for (int i = 0; i < jsonArray.size(); i++)
        {
            JsonObject jsonObject = jsonArray.get(i).getAsJsonObject();
            String className = jsonObject.get("class").getAsString();
            
            if(className.equals("Customer"))
            {
                users[i] = new Customer(
                        jsonObject.get("username").getAsString(),
                        jsonObject.get("password").getAsString()
                );
            }
            else if(className.equals("Admin"))
            {
                users[i] = new Admin(
                        jsonObject.get("username").getAsString(),
                        jsonObject.get("password").getAsString()
                );
            }
            else if(className.equals("DeliveryStaff"))
            {
                users[i] = new DeliveryStaff(
                        jsonObject.get("username").getAsString(),
                        jsonObject.get("password").getAsString()
                );
            }
        }
        return users;
    }
    
    public static User verifyUser(String username, String password)
    {
        User[] users = getUserList();
        for(User user : users)
        {
            if(user.username.equals(username) && user.password.equals(password))
            {
                return user;
            }
        }
        return null;
    }
    
    public static Boolean usernameExists(String username)
    {
        User[] users = getUserList();
        if(users != null)
        {
            for(User user : users)
            {
                if(user.username.equals(username))
                {
                    return true;
                }
            }
        }
        return false;
    }
    
    public abstract void register() throws UsernameExistsException, IOException;
}
