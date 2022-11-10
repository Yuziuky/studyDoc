
String host="";
String port="";
Properties props = new Properties();
props.setProperty("mail.smtp.host",host);//smpt主机名
props.setProperty("mail.smtp.port",port);//主机端口
props.setProperty("mail.smtp.auth","false");//是否需要用户认证

Authenticator authenticator=null;
if("mail.smtp.auth"==true){
  String username="";
  String password="";
  authenticator= new Authenticator(){
    protected PasswordAuthentication getPasswordAuthentication(){
      return new PasswordAuthentication(username,password);
    }
  }
}

Session session = Session.getDefaultInstance(props,authenticator);

//构建邮件
Message message = new Message(session);
//发件人
message.setFrom(new InternetAddress("邮箱地址"));
//收件人
//RecipientType.TO 收件人
//RecipientType.CC 抄送
//RecipientType.BCC 
message.setRecipient(Message.RecipientType.TO,new InternetAddress("邮箱地址"));
message.setSubject("");
message.setSentDate(new Date());

message.setContent("内容","text/html;charset=UTF-8");
message.saveChanges();
Transport.send(message);
