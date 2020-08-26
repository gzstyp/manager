package com.fwtai.tool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Properties;

/**
 * 邮件发送类，用于发送邮件及附件,支持mime格式及Html，支持多附件
 * @作者 田应平
 * @版本 v1.0
 * @创建时间 2019/4/17 14:31
 * @QQ号码 444141300
 * @Email service@dwlai.com
 * @官网 http://www.fwtai.com
*/
public class ToolMail{

    protected Logger logger = LoggerFactory.getLogger(getClass());

    private MimeMessage mimeMsg; //MIME邮件对象

    private Session session; //邮件会话对象

    private Properties props; //系统属性

    private Multipart mp; //Multipart对象,邮件内容,标题,附件等内容均添加到其中后再生成MimeMessage对象

    private String authentication = "false"; //smtp是否需要认证

    private String username = ""; //smtp认证用户名和密码

    private String password = "";

    private String from = "";

    private String to = "";

    private String cc = "";

    private String bcc = "";

    private String body = "";

    private String subject = "";

    private String host = "";

    private String reply = "";

    private String sender = "";

    private String date = "";

    private boolean textmail = true;

    private String mailtype = PLAIN;

    private static final String HTML = "text/html;charset=UTF-8";

    private static final String PLAIN = "text/plain; charset=UTF-8";

    private ArrayList attachment = new ArrayList();

    /**
     *
     */
    public ToolMail(){
        this(true);
    }

    public ToolMail(boolean textmail){
        this.textmail = textmail;
        this.mailtype = textmail ? PLAIN : HTML;
    }

    public ToolMail(String from,String to,String subject,String body){
        this();
        loadDefault();
    }

    private boolean createMimeMessage(){
        try{
            session = Session.getDefaultInstance(props,null); //获得邮件会话对象
        }catch(Exception e){
            logger.error("createMimeMessage():获取邮件会话对象时发生错误");
            return false;
        }
        try{
            mimeMsg = new MimeMessage(session); //创建MIME邮件对象
            mp = new MimeMultipart();
            return true;
        }catch(Exception e){
            logger.error("createMimeMessage():创建MIME邮件对象失败！");
            return false;
        }
    }

    private boolean initMail(){
        if(props == null){
            props = System.getProperties(); //获得系统属性对象
        }
        if("".equals(host)){ // 设置主机
            loadDefault();
        }
        props.put("mail.smtp.host",host); //设置SMTP主机
        if(!this.createMimeMessage())
            return false;
        props.put("mail.smtp.auth",authentication); //设置认证
        try{
            if(!"".equals(subject)) // 设置标题
                mimeMsg.setSubject(subject);
            if(!"".equals(from)) //设置发信人
                mimeMsg.setFrom(new InternetAddress(from));
            if(!"".equals(to)) // 设置收信人
                mimeMsg.setRecipients(Message.RecipientType.TO,InternetAddress.parse(to));
            if(!"".equals(cc)) //  设置抄送
                mimeMsg.setRecipients(Message.RecipientType.CC,(Address[]) InternetAddress.parse(cc));
            if(!"".equals(bcc)) // 设置密件抄送
                mimeMsg.setRecipients(Message.RecipientType.BCC,(Address[]) InternetAddress.parse(bcc));
            if(!"".equals(reply)) //设置回复
                mimeMsg.setReplyTo((Address[]) InternetAddress.parse(reply));
            //    if (!"".equals(date)) //设置日期
            //     mimeMsg.setSentDate(new Date(date));
            if(!"".equals(body)){ // 设置内容
                if(!this.textmail){
                    BodyPart bp = new MimeBodyPart();
                    bp.setContent(body,mailtype);
                    mp.addBodyPart(bp);
                }else{
                    mimeMsg.setText(body);
                }
            }
            if(!attachment.isEmpty()){ // 设置附件
                for(Iterator it = attachment.iterator(); it.hasNext(); ){
                    BodyPart bpr = new MimeBodyPart();
                    FileDataSource fileds = new FileDataSource((String) it.next());
                    bpr.setDataHandler(new DataHandler(fileds));
                    bpr.setFileName(fileds.getName());
                    mp.addBodyPart(bpr);
                }
            }
            return true;
        }catch(Exception e){
            logger.error("initMail():设置邮件发生错误！");
            return false;
        }
    }

    public boolean Send(){
        try{
            if(initMail()){
                if(mp.getCount() > 0)
                    mimeMsg.setContent(mp);
                mimeMsg.saveChanges();
                logger.error("正在发送邮件....");
                Session mailSession = Session.getInstance(props,null);
                Transport transport = mailSession.getTransport("smtp");
                transport.connect((String) props.get("mail.smtp.host"),username,password);
                transport.sendMessage(mimeMsg,mimeMsg.getAllRecipients());
                logger.error("发送邮件成功！");
                transport.close();
                return true;
            }else{
                return false;
            }
        }catch(Exception e){
            logger.error("邮件发送失败！");
            return false;
        }
    }

    private void loadDefault(){
    }

    public void setCc(String cc){
        this.cc = cc;
    }

    public void setBcc(String bcc){
        this.bcc = bcc;
    }

    public void setHost(String host,boolean auth){
        this.host = host;
        this.authentication = String.valueOf(auth);
    }

    public void setHost(String host,boolean auth,String username,String password){
        setHost(host,auth);
        setUsername(username);
        setPassword(password);
    }

    public void setHost(String host,String username,String password){
        setHost(host,true);
        setUsername(username);
        setPassword(password);
    }

    public void setHost(String host){
        setHost(host,true);
    }

    public void setFrom(String from){
        this.from = from;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public void setTo(String to){
        this.to = to;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public void setAttachment(String filename){
        this.attachment.add(filename);
    }

    public void setBody(String body){
        this.body = body;
    }

    public void setSubject(String subject){
        this.subject = subject;
    }

    public void setReply(String reply){
        this.reply = reply;
    }

    public void setSender(String sender){
        this.sender = sender;
    }

    public static void main(String[] argc){
        if(argc.length == 0){
            System.out.println("Useage:SendMail [smtp] [user] [password] [to] [body]");
        }else{
            ToolMail sm = new ToolMail();
            sm.setHost(argc[0],argc[1],argc[2]);
            sm.setTo(argc[3]);
            sm.setBody(argc[4]);
            if(sm.Send()){
                System.out.print("Send successful.");
            }else{
                System.out.print("Send failed.");
            }
        }
    }
}