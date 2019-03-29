package com.webcheckers.util;

import com.webcheckers.util.Message.Type;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class MessageTest {

   private final String infoMessage = "This is the message for the info Message object";
   private final String errorMessage = "This is the message for the error Message object";

   private Message testInfoMessage;
   private Message testErrorMessage;
   private Message yetAnotherTestInfoMessage;

   private Type errorType = Type.ERROR;
   private Type infoType = Type.INFO;

   @BeforeEach
   public void setup(){
      testInfoMessage = Message.info(infoMessage);
      testErrorMessage = Message.error(errorMessage);
      yetAnotherTestInfoMessage = Message.info(infoMessage);
   }

   @Test
   public void getTextTest(){
      assertEquals(infoMessage, testInfoMessage.getText());
      assertEquals(errorMessage, testErrorMessage.getText());
      assertNotEquals(infoMessage, testErrorMessage.getText());
   }

   @Test
   public void getTypeTest(){
      assertEquals(errorType, testErrorMessage.getType());
      assertEquals(infoType, testInfoMessage.getType());
      assertNotEquals(infoType, testErrorMessage.getType());
   }

   @Test
   public void isSuccessfulTest(){
      assertTrue(testInfoMessage.isSuccessful());
      assertFalse(testErrorMessage.isSuccessful());
   }

   @Test
   public void equalsTest(){
      final String testStr = "";
      assertFalse(infoMessage.equals(testStr));

      assertFalse(testInfoMessage.equals(testErrorMessage));
      assertTrue(testInfoMessage.equals(yetAnotherTestInfoMessage));
   }

}
