#include <ESP8266WiFi.h>
#include<FirebaseArduino.h>
#define FIREBASE_HOST "techhome-e0574.firebaseio.com"                    
#define FIREBASE_AUTH "yoezZbKI1mZmcIlAAsMRC2Mdf4tinEQ5k02uQl"       
#define WIFI_SSID "Bilol Habibi"                                        
#define WIFI_PASSWORD "ragnar!@#$%"                                

#define Relay1 12 //D6
int val1;

#define Relay2 14 //D2
int val2;

#define Relay3 4  //D1
int val3;

#define Relay4 5 //D5
int val4;

void setup() 
{
  //Serial.begin(115200); 
  pinMode(Relay1,OUTPUT);
  pinMode(Relay2,OUTPUT);
  pinMode(Relay3,OUTPUT);
  pinMode(Relay4,OUTPUT);

  digitalWrite(Relay1,HIGH);
  digitalWrite(Relay2,HIGH);
  digitalWrite(Relay3,HIGH);
  digitalWrite(Relay4,HIGH);



  
  WiFi.begin(WIFI_SSID,WIFI_PASSWORD);
  Serial.print("connecting");
  while (WiFi.status()!=WL_CONNECTED){
    Serial.print(".");
    delay(500);
  }
  Serial.println();
  Serial.print("connected:");
  Serial.println(WiFi.localIP());

  Firebase.begin(FIREBASE_HOST,FIREBASE_AUTH);
  
}
void firebasereconnect()
{
  Serial.println("Trying to reconnect");
    Firebase.begin(FIREBASE_HOST, FIREBASE_AUTH);
  }

void loop() 
{
  if (Firebase.failed())
      {
      Serial.print("setting number failed:");
      Serial.println(Firebase.error());
      firebasereconnect();
      return;
      }
      
  val1=Firebase.getString("button_one").toInt();                                        
  
  if(val1==1)                                                             
     {
      digitalWrite(Relay1,LOW);
      Serial.println("light 1 ON");
    }
    else if(val1==0)                                                      
    {                                      
      digitalWrite(Relay1,HIGH);
      Serial.println("light 1 OFF");
    }

  val2=Firebase.getString("button_two").toInt();                                        
  
  if(val2==1)                                                             
      digitalWrite(Relay2,LOW);
      Serial.println("light 2 ON");
    }
    else if(val2==0)                                                      
    {                                      
      digitalWrite(Relay2,HIGH);
      Serial.println("light 2 OFF");
    }

   val3=Firebase.getString("button_three").toInt();                                 
  
  if(val3==1)                                                             
     {
      digitalWrite(Relay3,LOW);
      Serial.println("light 3 ON");
    }
    else if(val3==0)                                                      
    {                                      
      digitalWrite(Relay3,HIGH);
      Serial.println("light 3 OFF");
    }

   val4=Firebase.getString("button_four").toInt();                                 
  
  if(val4==1)                                                             
     {
      digitalWrite(Relay4,LOW);
      Serial.println("light 4 ON");
    }
    else if(val4==0)                                                      
    {                                      
      digitalWrite(Relay4,HIGH);
      Serial.println("light 4 OFF");
    }    
}
