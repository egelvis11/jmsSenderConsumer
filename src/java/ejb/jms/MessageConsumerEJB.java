/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.jms;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.ejb.MessageDrivenContext;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 *
 * @author ADMIN
 */
@MessageDriven(mappedName="jms/Queue", activationConfig = {
    @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
    @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge")
})
public class MessageConsumerEJB implements MessageListener {

    public MessageConsumerEJB() {
    }

    private static MessageDrivenContext context;
    
    @Override
    public void onMessage(Message message) {
        TextMessage msg = null;

        try {
            if (message instanceof TextMessage) {
                msg = (TextMessage) message;
                System.out.println("MESSAGE BEAN: Message received: "+ msg.getText()+ " "+msg.getText().getBytes().length);
                Thread.sleep(10000);
                System.out.println("End read message "+msg.getJMSCorrelationID());
            } else {
                System.out.println("Message of wrong type: "+ message.getClass().getName());
            }
        } catch (JMSException e) {
            e.printStackTrace();
            context.setRollbackOnly();
        } catch (Throwable te) {
            te.printStackTrace();
        }
    }

}
