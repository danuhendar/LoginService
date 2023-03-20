import java.io.FileInputStream;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Properties;
import java.util.UUID;

import org.bson.Document;
import org.bson.json.JsonWriter;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;




public class IDMLogin {
	 
    
   
    Global_function gf = new Global_function(true);
    Interface_ga inter_login;
    Connection con;
    SQLConnection sqlcon = new SQLConnection();
    MqttClient client_transreport_primary;
    int counter = 1;
    
	public IDMLogin() {
		
	}
	
	
	public String get_tanggal_curdate(){
        String res = "";
        try {
              int year = Calendar.getInstance().get(Calendar.YEAR);
              int month = Calendar.getInstance().get(Calendar.MONTH)+1;
              
              String bulan = "";
              if(month<10){
                  bulan = "0"+month;
              }else{
                  bulan = ""+month;
              }
              int d = Calendar.getInstance().get(Calendar.DATE);
              String tanggal = "";
              if(d<10){
                  tanggal = "0"+d;
              }else{
                  tanggal = ""+d;
              }
              String concat = year+""+bulan+""+tanggal;
              res = concat;                      
        } catch (Exception e) {
              res = "";  
        }
        
        return res;
    }
    public String get_tanggal_curdate_curtime(){
        String res = "";
        try {
              int year = Calendar.getInstance().get(Calendar.YEAR);
              int month = Calendar.getInstance().get(Calendar.MONTH)+1;
              String bulan = "";
              if(month<10){
                  bulan = "0"+month;
              }else{
                  bulan = ""+month;
              }
              int d = Calendar.getInstance().get(Calendar.DATE);
              String tanggal = "";
              if(d<10){
                  tanggal = "0"+d;
              }else{
                  tanggal = ""+d;
              }
              int h = Calendar.getInstance().get(Calendar.HOUR);
              String jam = "";
              if(h<10){
                  jam = "0"+h;
              }else{
                  jam = ""+h;
              }
              int min = Calendar.getInstance().get(Calendar.MINUTE);
              String menit = "";
              if(min<10){
                  menit = "0"+min;
              }else{
                  menit = ""+min;
              }
              int sec = Calendar.getInstance().get(Calendar.SECOND);
              String detik = "";
              if(sec<10){
                  detik = "0"+sec;
              }else{
                  detik = ""+sec;
              }
              String concat = year+""+bulan+""+tanggal+""+jam+""+menit+""+detik;
              res = concat;                      
        } catch (Exception e) {
              res = "";  
        }
        
        return res;
    }
    
    public String get_tanggal_curdate_curtime_format(){
        String res = "";
        try {
             LocalDateTime myDateObj = LocalDateTime.now();
             //System.out.println("Before formatting: " + myDateObj);
             
             DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

             String formattedDate = myDateObj.format(myFormatObj);
             System.out.println("After formatting: " + formattedDate);
             res = formattedDate;                      
        } catch (Exception e) {
              res = "";  
        }
        
        return res;
    }
	
	String Parser_TASK,
    Parser_ID,
    Parser_SOURCE,
    Parser_COMMAND,
    Parser_OTP,
    Parser_TANGGAL_JAM,
    Parser_VERSI,
    Parser_HASIL,
    Parser_FROM,
    Parser_TO,
    Parser_SN_HDD,
    Parser_IP_ADDRESS,
    Parser_STATION,
    Parser_CABANG,
    Parser_NAMA_FILE,
    Parser_CHAT_MESSAGE,
    Parser_REMOTE_PATH,
    Parser_LOCAL_PATH,
    Parser_SUB_ID; 
    
    public void UnpackJSON(String json_message){
        
        JSONParser parser = new JSONParser();
        JSONObject obj = null;
        try {
            obj = (JSONObject) parser.parse(json_message);
        } catch (org.json.simple.parser.ParseException ex) {
            System.out.println("message json : "+json_message);
            System.out.println("message error : "+ex.getMessage());
            //ex.printStackTrace();
            //Logger.getLogger(IDMReport.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            Parser_TASK = obj.get("TASK").toString();
        } catch (Exception ex) {
             Parser_TASK = "";
        }       
        try{
            Parser_ID = obj.get("ID").toString();
        }catch(Exception exc){
            Parser_ID = "";
        }
        try{
            Parser_SOURCE = obj.get("SOURCE").toString();
        }catch(Exception exc){
            Parser_SOURCE = "";
        }
        try{
            Parser_COMMAND = obj.get("COMMAND").toString();
        }catch(Exception exc){
            Parser_COMMAND = "";
        }
          try{
           Parser_OTP = obj.get("OTP").toString();
        }catch(Exception exc){
            Parser_OTP = "";
        }
        
        
        try{
           Parser_TANGGAL_JAM = obj.get("TANGGAL_JAM").toString();
        }catch(Exception exc){
            Parser_TANGGAL_JAM = "";
        }
        try{
            Parser_VERSI = obj.get("RESULT").toString().split("_")[7];
        }catch(Exception exc){
            try{
                Parser_VERSI = obj.get("VERSI").toString();
            }catch(Exception exc1){ Parser_VERSI = "";}
            
        }

        try{
            Parser_HASIL = obj.get("HASIL").toString();
            Parser_FROM = obj.get("FROM").toString();
            Parser_TO = obj.get("TO").toString();

        }catch(Exception exc){
            Parser_HASIL = "";
            Parser_FROM = "";
            Parser_TO = "";
        }
       
        try{
            Parser_SN_HDD = obj.get("SN_HDD").toString();
        }catch(Exception exc){
            try{
                Parser_SN_HDD = obj.get("SN_HDD").toString();
            }catch(Exception exc1){Parser_SN_HDD = "";}
            
        }
        try{
            Parser_IP_ADDRESS = obj.get("IP_ADDRESS").toString();
        }catch(Exception exc){
            try{
                Parser_IP_ADDRESS = obj.get("IP_ADDRESS").toString();    
            }catch(Exception exc1){
                Parser_IP_ADDRESS = "";
            }

        }
        
        try{
            Parser_STATION = obj.get("STATION").toString();
        }catch(Exception exc){
            try{
                Parser_STATION = obj.get("STATION").toString();
            }catch(Exception exc1){Parser_STATION = "";}
            
        }
        
        try{
           Parser_CABANG = obj.get("CABANG").toString();
        }catch(Exception exc){
            try{
                Parser_CABANG = obj.get("CABANG").toString();
            }catch(Exception exc1){Parser_CABANG = "";}
        }
        
        try{
            Parser_NAMA_FILE = obj.get("NAMA_FILE").toString();
        }catch(Exception exc){
            Parser_NAMA_FILE = "";
        }
        try{
            Parser_CHAT_MESSAGE = obj.get("CHAT_MESSAGE").toString();
        }catch(Exception exc){
            Parser_CHAT_MESSAGE = "";
        }
        try{
            Parser_REMOTE_PATH = obj.get("REMOTE_PATH").toString();
        }catch(Exception exc){
            Parser_REMOTE_PATH = "";
        }
        try{
            Parser_LOCAL_PATH = obj.get("LOCAL_PATH").toString();
        }catch(Exception exc){
            Parser_LOCAL_PATH = "";
        }
        try{
            Parser_SUB_ID = obj.get("SUB_ID").toString();
        }catch(Exception exc){
            Parser_SUB_ID = "";
        }
        
     }
    
    public String CekVersion(String version,String kode_cabang){
        String res = "";
        String res_version = "";
        String res_status = "";
        try {
 
            String query_ver = "SELECT a.versi,a.STATUS FROM versi a WHERE KDCAB_MAIN RLIKE '"+kode_cabang+"' ORDER BY VERSI ASC LIMIT 0,1;";
            
            //System.err.println("query_ver : "+query_ver);
           
            String get_data_version = gf.GetTransReport(query_ver, 2, false,false);//inter_login.call_get_procedure(query_ver,1, false);
            String sp_record_versi[] = get_data_version.split("~");
            for(int a = 0;a<sp_record_versi.length;a++) {
          	  String sp_field_versi[] = sp_record_versi[a].split("%");
          	  res_version = sp_field_versi[0];
          	  res_status = sp_field_versi[1];
            }
            
            int res_version_db = Integer.parseInt(res_version.replace(".", ""));
            //System.out.println("res_version_db : "+res_version_db);
            int res_version_app = Integer.parseInt(version.replace(".", ""));
            //System.out.println("res_version_app : "+res_version_app);
            if(res_version_app == res_version_db)
            {
                res = "OK"+"|"+res_version+"|"+res_status;
            }
            else if(res_version_app > res_version_db)
            {
                res = "OK"+"|"+res_version+"|"+res_status;
            }
            else
            {
                res = "NOK"+"|"+res_version+"|"+res_status;
            }
           
        } catch (Exception e) {
      	  e.printStackTrace();
            res = e.getMessage();
            res = "NOK"+"|"+res_version+"|"+res_status;
        }
        
        return res;
    }    
      
      
    public String get_attr_db() {
    	String res = "";
    	 try {
         	String query_get_pass_sql = "SELECT ID,USER,PASS,DB,PORT,IS_STATUS FROM `m_pass_sql` WHERE IS_AKTIF = '1';";
               //System.out.println("query_get_pass_vnc : "+query_get_pass_vnc);
               String get_pass_sql = gf.GetTransReport(query_get_pass_sql, 6, false,false);
               //System.out.println(get_pass_vnc);
               String sp_record_sql[] = get_pass_sql.split("~");
               
               String res_sql = "";
               JSONObject obj_res = new JSONObject();
               for(int a = 0;a<sp_record_sql.length;a++) {
               	JSONObject obj_cab = new JSONObject();
               	String sp_field_sql[] = sp_record_sql[a].split("%");
               	String ID = sp_field_sql[0];
               	String USER = sp_field_sql[1];
               	String PASS = sp_field_sql[2];
               	String DB = sp_field_sql[3];
               	String PORT = sp_field_sql[4];
               	String IS_STATUS = sp_field_sql[5];
               	
               	obj_cab.put("ID", ID);
               	obj_cab.put("USER", USER);
               	obj_cab.put("PASS", PASS); 
               	obj_cab.put("PORT", PORT); 
               	obj_cab.put("IS_STATUS", IS_STATUS); 
               	obj_res.put(DB,obj_cab);
               }
               
               res = obj_res.toJSONString();
               //System.out.println(Parser_NAMA_FILE);
            }catch(Exception exc) {
           	 exc.printStackTrace();
           	res = "";
            }     
            return res;
    }
    public String get_vnc(String branch_code) {
    	String res = "";
    	try {
        	String query_get_pass_vnc = "SELECT KDCAB,NON_IKIOS,IKIOS FROM `m_pass_vnc` WHERE KDCAB IN('"+branch_code.replaceAll(",", "','")+"');";
              //System.out.println("query_get_pass_vnc : "+query_get_pass_vnc);
              String get_pass_vnc = gf.GetTransReport(query_get_pass_vnc, 3, false,false);//inter_login.call_get_procedure(query_get_nama, 4 , false);
              //System.out.println(get_pass_vnc);
              String sp_record_vnc[] = get_pass_vnc.split("~");
              
              String res_vnc = "";
              JSONObject obj_res = new JSONObject();
              for(int a = 0;a<sp_record_vnc.length;a++) {
              	JSONObject obj_cab = new JSONObject();
              	String sp_field_vnc[] = sp_record_vnc[a].split("%");
              	String KDCAB = sp_field_vnc[0];
              	String NON_IKIOS = sp_field_vnc[1];
              	String IKIOS = sp_field_vnc[2];
              	
              	obj_cab.put("NON_IKIOS", NON_IKIOS);
              	obj_cab.put("IKIOS", IKIOS);
              	obj_res.put(KDCAB,obj_cab);
              	
              }
              
              res = obj_res.toJSONString();
          }catch(Exception exc) {
          	 exc.printStackTrace();
          	 res = "";
          }
    	
    	return res;
    }
      
    public String get_menu(String username) {
    	 String res = "";
    	 try {
         	String query_for_management_menu = "SELECT a.ID,a.KODE_JABATAN AS JABATAN,a.KODE_USER AS NIK,b.KODE_SUB_MENU,b.CONTENT AS NAMA_MENU FROM m_modul_akses_user a INNER JOIN m_sub_menu b ON a.KODE_SUB_MENU=b.KODE_SUB_MENU WHERE a.KODE_USER = '"+username+"' AND b.PLATFORM = 'Desktop' GROUP BY a.KODE_SUB_MENU ORDER BY a.KODE_SUB_MENU ASC";
             String get = gf.GetTransReport(query_for_management_menu, 5, false,false);
             String sp_record[] = get.split("~");
             JSONObject obj_res = new JSONObject();
             JSONArray arr_ = new JSONArray();
             for(int b = 0;b<sp_record.length;b++) {
             	String sp_field[] = sp_record[b].split("%");
             	String id = sp_field[0];
             	String jabatan = sp_field[1];
             	String nik = sp_field[2];
             	String kode_sub_menu = sp_field[3];
             	String nama_menu = sp_field[4];
             	
             	 
             	JSONObject data = new JSONObject();
                 data.put("ID", id);
                 data.put("JABATAN", jabatan);
                 data.put("NIK", nik);
                 data.put("KODE_SUB_MENU", kode_sub_menu);
                 data.put("NAMA_MENU", nama_menu);
                 arr_.add(data);
             }
             
             res = arr_.toJSONString();
         }catch(Exception exc) {
         	//exc.printStackTrace();
         	System.out.println("User : "+username+" belum memiliki menu pada idmcommander");
         	res = "";
         }
    	 
    	 return res;
    }  
    
    public String get_m_pass_sql() {
    	 String res = "";
    	 try {
         	String query_get_pass_sql = "SELECT GROUP_CONCAT(DB) AS NAMA_DB FROM `m_pass_sql` WHERE IS_AKTIF = '1';";
            //System.out.println("query_get_pass_vnc : "+query_get_pass_vnc);
            String get_pass_sql = gf.GetTransReport(query_get_pass_sql, 1, true,false);
            res = get_pass_sql;
         }catch(Exception exc) {
           	 exc.printStackTrace();
           	res = "";
         }    
    	 
    	 return res;
    }
    
    public String get_pattern_command() {
    	String res = "";
    	try {
    		 String get_pattern = gf.GetTransReport("SELECT JABATAN,COMMAND,TIPE_BC,NIK_WHITELIST FROM pattern_command;", 4, false,false);
             String sp_record[] = get_pattern.split("~");
             JSONObject obj_res = new JSONObject();
             JSONArray arr_ = new JSONArray();
             for(int b = 0;b<sp_record.length;b++) {
             	String sp_field[] = sp_record[b].split("%");
             	String jabatan = sp_field[0];
             	String command = sp_field[1];
             	String tipe_bc = sp_field[2];
             	String nik_whitelist = sp_field[3];
             	 
             	 JSONObject data = new JSONObject();
                 data.put("JABATAN", jabatan);
                 data.put("PATTERN_COMMAND", command);
                 data.put("TIPE_BC", tipe_bc);
                 data.put("NIK_WHITELIST", nik_whitelist);
                 arr_.add(data);
             }
             
         	res = arr_.toJSONString();
    	}catch(Exception exc) {
    		res = "";
    	}
    	
    	return res;
    }
    
    public String get_m_registry() {
    	String res = "";
    	try {
    		JSONArray arr = new JSONArray();
    		JSONObject obj = new JSONObject();
    		obj.put("lock_registry", "");
    		obj.put("unlock_registry", "");
    		arr.add(obj);
    		String get_pattern = arr.toJSONString();
    		//gf.GetTransReport("SELECT lock_registry,unlock_registry FROM m_registry WHERE recid = '';", 2, false,true);
    		res = get_pattern;
    	}catch(Exception exc) {
    		exc.printStackTrace();
    		res = exc.toString();
    	}
    	
    	return res;
    }
    
    public Boolean isVerifikasiSuccess(String location,String ip_address_from_idmcommander) {
    	Boolean res_verifikasi = false;
    	String res = "";
    	try {
    		String query_verifikasi = "SELECT LIST_SEGMENT_IP FROM m_ip_whitelist_idmcommand WHERE JENIS = 'IDMCOMMANDER' AND KDCAB = '"+location+"';";
    		String get = gf.GetTransReport(query_verifikasi, 1, true,false);
    		Object o = JSONValue.parse(get);
    		
    		JSONParser parser = new JSONParser();
            JSONObject obj = null;
            try {
                 obj = (JSONObject) parser.parse(o.toString());
                 JSONArray slideContent = (JSONArray) obj.get("LIST_IP");
                 Iterator i = slideContent.iterator();
                 //Boolean res_cek = false;
                 while (i.hasNext()) {
                     //JSONObject slide = (JSONObject) i.next();
                	 String res_list_ip_master = i.next().toString();
                	 
                	 if(ip_address_from_idmcommander.indexOf(res_list_ip_master) == -1) {
                		 res = "Akses ditolak, IP : "+ip_address_from_idmcommander+" tidak terdaftar. Silahkan hubungi Administrator !!!";
                		 gf.WriteLog("hasil_verifikasi", res, true);
                		 res_verifikasi = false;
                	 }else {
                		 res = "Akses di izinkan, IP : "+ip_address_from_idmcommander;
                		 gf.WriteLog("hasil_verifikasi", res, true);
                		 res_verifikasi = true;
                		 break;
                	 }
                     //System.out.println(i.next());
                 }
                 
            } catch (org.json.simple.parser.ParseException ex) {
               ex.printStackTrace();
               res_verifikasi = false;
               gf.WriteLog("error_verifikasi", res, true);
            }
            
    		
    		//res = "OK";
    	}catch(Exception exc) {
    		exc.printStackTrace();
    		String content_error = exc.toString();
    		res = "GAGAL : "+content_error;
    		gf.WriteLog("error_verifikasi", res, true);
    		//gf.WriteLog("error_verifikasi", content_error, true);
    		res_verifikasi = false;
    	}
    	
    	return res_verifikasi;
	}
    
    public void send_TOIDMCommandV2Bot(String task,String topic,String res_message_command) {
    	try {
    		
             Parser_CHAT_MESSAGE = topic;
             Parser_SOURCE = "IDMReporter";
             Parser_IP_ADDRESS = "172.24.52.3";
             Parser_FROM = "IDMReporter";
             Parser_TO = "IDMCommandV2Bot";
             Parser_TASK = task;
             
             String res_message_idmcommandv2bot = gf.CreateMessage(Parser_TASK,Parser_ID,Parser_SOURCE,res_message_command,Parser_OTP,Parser_TANGGAL_JAM,Parser_VERSI,Parser_HASIL,Parser_FROM,Parser_TO,Parser_SN_HDD,Parser_IP_ADDRESS,Parser_STATION,Parser_CABANG,"",Parser_NAMA_FILE,Parser_CHAT_MESSAGE,Parser_REMOTE_PATH,Parser_LOCAL_PATH,Parser_SUB_ID);
             System.err.println("Message SENT IDMCommandV2Bot : "+res_message_idmcommandv2bot);
             
             byte[] convert_message_idmcommandv2bot = res_message_idmcommandv2bot.getBytes("US-ASCII");
             byte[] bytemessage_idmcommandv2bot = gf.compress(convert_message_idmcommandv2bot);
            
             gf.PublishMessageDocumenter(true,1,topic,bytemessage_idmcommandv2bot,counter,res_message_idmcommandv2bot,1); 
             gf.WriteLog("log_idmreporter", "SEND TO IDMCommandV2Bot : "+res_message_idmcommandv2bot+"\n"+topic, true);
             System.out.println("SEND TO IDMCommandV2Bot : "+res_message_idmcommandv2bot+"\n"+topic);
    	}catch(Exception exc) {
    		exc.printStackTrace();
    		String content_error = exc.toString();
            gf.WriteLog("error_send_bot", content_error, true);
    	}
    }
    
    public String get_identitas_user(String nik_bawahan,String ip_login,String type,String via,String pass) {
    	String res = "";
    	try {
    		
    		String tahun_bulan_tanggal = gf.get_tanggal_curdate().replaceAll("-", "");
            String nama_table_create = "transreport"+tahun_bulan_tanggal;
            
    		String query = "SELECT v.*,\r\n"
    				+ "					(SELECT\r\n"
    				+ "						GROUP_CONCAT(l.ID,\"|\",k.NAMA,\"|\",k.CHAT_ID) AS CHAT_ID_ATASAN\r\n"
    				+ "						FROM m_struktur_jabatan l LEFT JOIN idm_org_structure k ON k.JABATAN=l.CONTENT \r\n"
    				+ "						WHERE l.ID > v.ID\r\n"
    				+ "							AND l.KODE = v.KODE\r\n"
    				+ "							AND k.LOCATION = v.LOCATION\r\n"
    				+ "							AND k.JABATAN NOT LIKE 'SUPPORT%'\r\n"
    				+ "							AND k.CHAT_ID IS NOT NULL \r\n"
    				+ "							AND k.CHAT_ID != '0' AND LENGTH(CHAT_ID) > 1\r\n"
    				+ "							) AS CHAT_ID_ATASAN\r\n"
    				+ "							\r\n"
    				+ "    					FROM (SELECT a.LOCATION,\r\n"
    				+ "    					a.NIK,\r\n"
    				+ "    					a.NAMA,\r\n"
    				+ "    					a.JABATAN,\r\n"
    				+ "    					a.BAGIAN,\r\n"
    				+ "    					a.CHAT_ID,\r\n"
    				+ "    					b.ID,\r\n"
    				+ "    					b.KODE,\r\n"
    				+ "    					(SELECT CONTENT FROM m_struktur_jabatan WHERE ID > b.ID AND KODE =  LEFT(a.LOCATION,1) ORDER BY ID ASC LIMIT 0,1) AS JABATAN_ATASAN,\r\n"
    				+ "    				IFNULL((SELECT CONCAT(MAX(ADDTIME),'#',IP) AS LAST_LOGIN FROM "+nama_table_create+" WHERE TASK = 'LOGIN' AND CMD LIKE '"+nik_bawahan+"%' ORDER BY ROWID DESC LIMIT 0,1),':') AS LAST_LOGIN\r\n"
    				+ "    				\r\n"
    				+ "    				FROM idm_org_structure a INNER JOIN m_struktur_jabatan b ON a.JABATAN=b.CONTENT\r\n"
    				+ "    				) v\r\n"
    				+ "    				WHERE v.NIK = '"+nik_bawahan+"'\r\n"
    				+ "    				GROUP BY v.NIK\r\n"
    				+ "    				;";
    		
    		System.out.println("query : "+query);
    		String get_identitas_user = gf.GetTransReport(query, 11,false,false);
    		String sp_record[] = get_identitas_user.split("~");
    		System.out.println("Jumlah Data : "+sp_record.length);
    		JSONObject obj_command_for_bot = new JSONObject();
    		for(int i = 0;i<sp_record.length;i++) {
    			String sp_field[] = sp_record[i].split("%");
    			String res_location = sp_field[0];
    			String res_nik = sp_field[1];
    			String res_nama = sp_field[2];
    			String res_jabatan = sp_field[3];
    			String res_bagian = sp_field[4];
    			String res_chat_id_user = sp_field[5];
    			String res_id = sp_field[6];
    			String res_kode = sp_field[7];
    			String res_jabatan_atasan = sp_field[8];
    			String res_last_login = sp_field[9];
    			String res_chat_id_atasan = sp_field[10];
    			obj_command_for_bot.put("LOCATION", res_location);
    			obj_command_for_bot.put("NIK", res_nik);
    			obj_command_for_bot.put("NAMA", res_nama);
    			obj_command_for_bot.put("JABATAN", res_jabatan);
    			obj_command_for_bot.put("BAGIAN", res_bagian);
    			obj_command_for_bot.put("LOGIN_DARI_IP", ip_login);
    			obj_command_for_bot.put("CHAT_ID_ATASAN", res_chat_id_atasan);
    			obj_command_for_bot.put("CHAT_ID_USER", res_chat_id_user);
    			obj_command_for_bot.put("LAST_LOGIN", res_last_login);
    			obj_command_for_bot.put("TYPE", type);
    			obj_command_for_bot.put("VIA", via);
    			obj_command_for_bot.put("PASS", pass);
    		}
    		
    		res = obj_command_for_bot.toJSONString();
    	}catch(Exception exc) {
    		exc.printStackTrace();
    		String content_error = exc.toString();
            gf.WriteLog("error_identitas_user", content_error, true);
            
    	}
    	
    	return res;
    }
    
    public void get_threshold_ganti_password() {
    	try {
    		String get_threshold_cek_ganti_password =  gf.GetTransReport("SELECT CONTENT FROM setting WHERE ITEM = 'THRESHOLD_GANTI_PASSWORD_HARI'", 1, true, false);
    		gf.en.setThreshold_ganti_password(get_threshold_cek_ganti_password);
    	}catch(Exception exc) {
    		gf.en.setThreshold_ganti_password("30");
    	}
    }
    
    public void LoginVerifikasiSuccess(String res_cabang,String message_ADT_Decompress,MqttClient client_,String res_pass_sql,String res_pattern_command,String res_attr_db) {
    	try {
    		
    		Parser_SOURCE = "IDMReporter";
            String from = Parser_TO;
            String to = Parser_FROM;
            Parser_FROM = from;
            Parser_TO = to;
            
            String username = Parser_COMMAND.substring(0, 10);
            String password = Parser_COMMAND.substring(11, Parser_COMMAND.length());
            //-- jika nik tidak terdaftar --//
            String query_cek_nik_tdk_terdaftar =  "SELECT Nik FROM idm_org_structure WHERE NIK = '"+username+"'";
            boolean res_query_cek_nik = gf.GetExistsData(query_cek_nik_tdk_terdaftar,true);
            System.out.println("USER ISEXISTS : "+res_query_cek_nik);
            if(res_query_cek_nik == false)
            {
                Parser_HASIL = "GAGAL|Nik Tidak Terdaftar";
                String res_message = gf.CreateMessage(Parser_TASK,Parser_ID,Parser_SOURCE,Parser_COMMAND,Parser_OTP,Parser_TANGGAL_JAM,Parser_VERSI,Parser_HASIL,Parser_FROM,Parser_TO,Parser_SN_HDD,Parser_IP_ADDRESS,Parser_STATION,res_cabang,"",Parser_NAMA_FILE,Parser_CHAT_MESSAGE,Parser_REMOTE_PATH,Parser_LOCAL_PATH,Parser_SUB_ID);
                gf.WriteLog("message_sent", "Message Sent : "+res_message , true);
                
                byte[] convert_message = res_message.getBytes("US-ASCII");
                byte[] bytemessage = gf.compress(convert_message);
                String topic_dest = "LOGIN/"+username+"/";
                gf.PublishMessageDocumenter(true,1,topic_dest,bytemessage,counter,res_message,1);
                //gf.PublishMessageDocumenter(true,2,topic_dest,bytemessage,counter,res_message,1);
            }
            else
            {
            	//-- jika username dan pass benar --//
    	        System.out.println(gf.get_tanggal_curdate_curtime()+" : PROSES CEK USER EXISTS");
            	String query_cek =  "SELECT Nik FROM idm_org_structure WHERE NIK = '"+username+"' AND Password = '"+password+"'";
    	        boolean query_cek_user = gf.GetExistsData(query_cek,false);
    	        System.out.println(gf.get_tanggal_curdate_curtime()+" : SELESAI CEK USER EXISTS");
    	        if(query_cek_user == true)
    	        {
    	        	//-- cek apakah password tidak diganti selama 30 Hari --//
    	        	boolean is_harus_ganti_password = false;
    	        	System.out.println(gf.get_tanggal_curdate_curtime()+" : PROSES CEK IDENTITAS USER");
    	        	String query_cek_password = "SELECT DATEDIFF(NOW(),UPD_PASSWORD) AS HARI,IS_AKTIF,JABATAN,LOCATION,NAMA,branch_code AS BRANCH_CODE FROM idm_org_structure WHERE NIK = '"+username+"' ";
    	        	String get_data_identitas = gf.GetTransReport(query_cek_password, 6, false, false);
    	        	System.out.println(gf.get_tanggal_curdate_curtime()+" : SELESAI CEK IDENTITAS USER");
    	        	String cek_user_last_ganti_password = get_data_identitas.split("~")[0].split("%")[0];
    	        	if(Integer.parseInt(cek_user_last_ganti_password) > Integer.parseInt(gf.en.getThreshold_ganti_password())) {
    	        		is_harus_ganti_password = true;
    	        	}else {
    	        		is_harus_ganti_password = false;
    	        	}
    	        	//-- cek apakah user di suspend atau tidak --//
    	            String cek_user_suspend =  get_data_identitas.split("~")[0].split("%")[1];
    	             if(cek_user_suspend.equals("1")) {
    	             	//-- cek apakah versi sudah cocok dengan service --//
    	            	System.out.println(gf.get_tanggal_curdate_curtime()+" : PROSES CEK VERSI");
    	                String res_cek_version = CekVersion(Parser_VERSI,res_cabang);
    	                System.out.println(gf.get_tanggal_curdate_curtime()+" : SELESAI CEK VERSI");
    	                //System.out.println("res_cek_version : "+res_cek_version);
    	                if(res_cek_version.substring(0,2).equals("OK")){
    	                    	
    	                    	
    	                        String nama = get_data_identitas.split("~")[0].split("%")[4];
    	                        String branch_code =  get_data_identitas.split("~")[0].split("%")[5];
    	                        
    	                        //String sql_jabatan = "SELECT JABATAN FROM idm_org_structure WHERE NIK = '"+to.split("_")[1]+"';";
    	                    	//System.out.println("sql_jabatan : "+sql_jabatan);
    	                    	String get_jabatan =  get_data_identitas.split("~")[0].split("%")[2];
    	                    	String get_all_branch_code = "";
    	                    	if(get_jabatan.equals("REGIONAL_MANAGER") || get_jabatan.equals("MANAGER_EDPHO") || get_jabatan.equals("EDP_HO")) {
    	                    		get_all_branch_code = gf.GetTransReport("SELECT GROUP_CONCAT(BRANCH_CODE) AS BRANCH_CODE FROM idm_org_branch", 1, true,false);
    	                    	}else {
    	                    		get_all_branch_code = branch_code;
    	                    	}
    	                    	//System.out.println("get_all_branch_code : "+get_all_branch_code);
    	                        
    	                        String location = get_data_identitas.split("~")[0].split("%")[3];
    	                        String Jabatan = get_jabatan;
    	                        
    	                        
    	                        System.out.println(gf.get_tanggal_curdate_curtime()+" : PROSES CEK REGISTRY");
    	                        String Parser_FILE_REGISTRY = get_m_registry(); 
    	                        System.out.println(gf.get_tanggal_curdate_curtime()+" : SELESAI CEK REGISTRY");
    	                        //String query_ver = "SELECT a.versi FROM versi a WHERE KDCAB_MAIN RLIKE '"+res_cabang+"' ORDER BY VERSI ASC LIMIT 0,1;";
    	                        //String get_versi = gf.GetTransReport(query_ver, 1, true,false);
    	                        String hasil = "SUKSES|"+nama+"|"+location+"|"+get_all_branch_code+"|"+Jabatan+"|"+Parser_FILE_REGISTRY+"|"+Parser_VERSI+"|"+is_harus_ganti_password+"|";
    	                        gf.WriteLog("login_success", hasil, true);
    	                        Parser_HASIL = hasil;
    	                        Parser_CABANG = location;
    	                        
    	                        //-- get_menu hak akses user --//
    	                        System.out.println(gf.get_tanggal_curdate_curtime()+" : PROSES CEK HASIL MENU");
    	                        String hasil_menu = get_menu(username);
    	                        System.out.println(gf.get_tanggal_curdate_curtime()+" : SELESAI CEK HASIL MENU");
    	                        Parser_CHAT_MESSAGE = hasil_menu;
    	                        //-- get vnc --//
    	                        System.out.println(gf.get_tanggal_curdate_curtime()+" : PROSES CEK HASIL VNC");
    	                        String hasil_vnc = get_vnc(branch_code);
    	                        System.out.println(gf.get_tanggal_curdate_curtime()+" : SELESAI CEK HASIL VNC");
    	                        Parser_REMOTE_PATH = hasil_vnc;
    	                        //------------ PASS DB MYSQL POS,SOPAGENT,DBL,DAN IKIOS --------------//
    	                        System.out.println(gf.get_tanggal_curdate_curtime()+" : PROSES CEK HASIL ATTRIBUTE");
    	                        String get_attr_db = res_attr_db;
    	                        System.out.println(gf.get_tanggal_curdate_curtime()+" : SELESAI CEK REGISTRY");
    	                        Parser_NAMA_FILE = get_attr_db;
    	                          //-- get pass Sql --//
    	                        String get_pass_sql = res_pass_sql;
    	                        Parser_LOCAL_PATH = get_pass_sql;
    	                        //-- get pattern command --//
    	                        String pattern_command = res_pattern_command;
    	                        Parser_OTP = pattern_command;
    	                        
    	                        
    	                        String res_message = gf.CreateMessage(Parser_TASK,Parser_ID,Parser_SOURCE,Parser_COMMAND,Parser_OTP,Parser_TANGGAL_JAM,Parser_VERSI,Parser_HASIL,Parser_FROM,Parser_TO,Parser_SN_HDD,Parser_IP_ADDRESS,Parser_STATION,res_cabang,"",Parser_NAMA_FILE,Parser_CHAT_MESSAGE,Parser_REMOTE_PATH,Parser_LOCAL_PATH,Parser_SUB_ID);
    	                        gf.WriteLog("message_sent", "Message Sent : "+res_message , true);
    	                        //System.out.println("res_message : "+res_message);
    	                        byte[] convert_message = res_message.getBytes("US-ASCII");
    	                        byte[] bytemessage = gf.compress(convert_message);
    	                        
    	                        //gf.PublishMessageAndDocumenter(topic_dest,bytemessage,counter,res_message,0);
    	                        
    	                        String topic_dest = "LOGIN/"+username+"/";
    	                        
    	                        MqttMessage message = new MqttMessage(bytemessage);
    	                        message.setQos(1);
    	                        client_transreport_primary.publish(topic_dest, message);
    	                        System.out.println("SUKSES PUBLISH : "+topic_dest+" LOKASI : "+res_cabang);
    	                        System.out.println("========================");
    	                        
    	                        //gf.PublishMessageDocumenter(true,1,topic_dest,bytemessage,counter,res_message,1);
    	                        //gf.PublishMessageDocumenter(true,2,topic_dest,bytemessage,counter,res_message,1);
    	
    	                }else if(res_cek_version.substring(0,3).equals("NOK")){
    	
    	                    
    	                        String query_ver = "SELECT a.versi FROM versi a WHERE KDCAB_MAIN RLIKE '"+res_cabang+"' ORDER BY VERSI ASC LIMIT 0,1;";
    	                        String get_versi = gf.GetTransReport(query_ver, 1, true,false);//inter_login.call_get_procedure(query_ver, 1, false).split("~")[0].split("%")[0];
    	                        String hasil = "GAGAL|VERSI APP DIBAWAH STANDART VERSI STANDART\n"+get_versi;
    	                        Parser_HASIL = hasil;
    	                        //Parser_COMMAND = sqlcon.EncodeString(Parser_COMMAND); 
    	                        Parser_CHAT_MESSAGE = res_cek_version;	
    	
    	                        String res_message = gf.CreateMessage(Parser_TASK,Parser_ID,Parser_SOURCE,Parser_COMMAND,Parser_OTP,Parser_TANGGAL_JAM,Parser_VERSI,Parser_HASIL,Parser_FROM,Parser_TO,Parser_SN_HDD,Parser_IP_ADDRESS,Parser_STATION,res_cabang,"",Parser_NAMA_FILE,Parser_CHAT_MESSAGE,Parser_REMOTE_PATH,Parser_LOCAL_PATH,Parser_SUB_ID);
    	                        //System.out.println("res_message : "+res_message);
    	                        gf.WriteLog("message_sent", "Message Sent : "+res_message , true);
    	                        byte[] convert_message = res_message.getBytes("US-ASCII");
    	                        byte[] bytemessage = gf.compress(convert_message);
    	                        
    	                        //gf.PublishMessageAndDocumenter(topic_dest,bytemessage,counter,res_message,0);
    	                        
    	                        String topic_dest = "LOGIN/"+username+"/";
    	                        gf.PublishMessageDocumenter(true,1,topic_dest,bytemessage,counter,res_message,1);
    	                        //gf.PublishMessageDocumenter(true,2,topic_dest,bytemessage,counter,res_message,1);
    	
    	                }else{
    	
    	                    
    	
    	                        String hasil = "GAGAL|"+res_cek_version;
    	                        Parser_HASIL = hasil;    
    	                        String res_message = gf.CreateMessage(Parser_TASK,Parser_ID,Parser_SOURCE,Parser_COMMAND,Parser_OTP,Parser_TANGGAL_JAM,Parser_VERSI,Parser_HASIL,Parser_FROM,Parser_TO,Parser_SN_HDD,Parser_IP_ADDRESS,Parser_STATION,res_cabang,"",Parser_NAMA_FILE,Parser_CHAT_MESSAGE,Parser_REMOTE_PATH,Parser_LOCAL_PATH,Parser_SUB_ID);
    	                        //System.out.println("res_message : "+res_message);
    	                        gf.WriteLog("message_sent", "Message Sent : "+res_message , true);
    	                        byte[] convert_message = res_message.getBytes("US-ASCII");
    	                        byte[] bytemessage = gf.compress(convert_message);
    	                        
    	                        //gf.PublishMessageAndDocumenter(topic_dest,bytemessage,counter,res_message,0);
    	                        
    	                        String topic_dest = "LOGIN/"+username+"/";
    	                        gf.PublishMessageDocumenter(true,1,topic_dest,bytemessage,counter,res_message,1);
    	                        //gf.PublishMessageDocumenter(true,2,topic_dest,bytemessage,counter,res_message,1);
    	
    	                }
    	            //-- jika user IS_AKTIF = 0 maka tampilkan pesan user ter-suspend hubungi administrator --//    
    	             }else {
    	             	String hasil = "GAGAL|USER TER-SUSPEND SILAHKAN HUBUNGI ADMINISTRATOR";
    	                Parser_HASIL = hasil;    
    	                //Parser_COMMAND = sqlcon.EncodeString(Parser_COMMAND); 
    	                
    	                String res_message = gf.CreateMessage(Parser_TASK,Parser_ID,Parser_SOURCE,Parser_COMMAND,Parser_OTP,Parser_TANGGAL_JAM,Parser_VERSI,Parser_HASIL,Parser_FROM,Parser_TO,Parser_SN_HDD,Parser_IP_ADDRESS,Parser_STATION,res_cabang,"",Parser_NAMA_FILE,Parser_CHAT_MESSAGE,Parser_REMOTE_PATH,Parser_LOCAL_PATH,Parser_SUB_ID);
    	                //System.out.println("res_message : "+res_message);
    	                gf.WriteLog("message_sent", "Message Sent : "+res_message , true);
    	                byte[] convert_message = res_message.getBytes("US-ASCII");
    	                byte[] bytemessage = gf.compress(convert_message);
    	                
    	                //gf.PublishMessageAndDocumenter(topic_dest,bytemessage,counter,res_message,0);
    	                
    	                String topic_dest = "LOGIN/"+username+"/";
    	                gf.PublishMessageDocumenter(true,1,topic_dest,bytemessage,counter,res_message,1);
    	                //gf.PublishMessageDocumenter(true,2,topic_dest,bytemessage,counter,res_message,1);
    	
    	             }
    	        }
    	        //-- USERNAME & PASSWORD TIDAK COCOK --/
    	        else
    	        {
    	            try{
    	
    	
    	                String hasil = "GAGAL|Password Salah";
    	                Parser_HASIL = hasil;
    	                //Parser_COMMAND = sqlcon.EncodeString(Parser_COMMAND); 
    	                //System.out.println("message : "+res_message);
    	                String res_message = gf.CreateMessage(Parser_TASK,Parser_ID,Parser_SOURCE,Parser_COMMAND,Parser_OTP,Parser_TANGGAL_JAM,Parser_VERSI,Parser_HASIL,Parser_FROM,Parser_TO,Parser_SN_HDD,Parser_IP_ADDRESS,Parser_STATION,res_cabang,"",Parser_NAMA_FILE,Parser_CHAT_MESSAGE,Parser_REMOTE_PATH,Parser_LOCAL_PATH,Parser_SUB_ID);
    	                //System.out.println("res_message : "+res_message);
    	                gf.WriteLog("message_sent", "Message Sent : "+res_message , true);
    	                byte[] convert_message = res_message.getBytes("US-ASCII");
    	                byte[] bytemessage = gf.compress(convert_message);
    	                
    	                //gf.PublishMessageAndDocumenter(topic_dest,bytemessage,counter,res_message,0);
    	                
    	                String topic_dest = "LOGIN/"+username+"/";
    	                gf.PublishMessageDocumenter(true,1,topic_dest,bytemessage,counter,res_message,1);
    	                //gf.PublishMessageDocumenter(true,2,topic_dest,bytemessage,counter,res_message,1);
    	
    	            }catch(Exception exc){
    	                //Hasil_eksekusi = "GAGAL EKSEKUSI QUERY IDMCOMMANDER_LOGIN";
    	            	String content_error =  message_ADT_Decompress+"\n"+exc.toString()+"\n";
    	                gf.WriteLog("error_logon", content_error, true);
    	                exc.printStackTrace();
    	            }     
    	        }
            }
    		
	    	
    	}catch(Exception exc) {
    		String content_error =  "ERROR LOGIN SUCCESS : "+"\n"+exc.toString()+"\n";
            gf.WriteLog("error_logon", content_error, true);
    		exc.printStackTrace();
    	}

    
    }
    
    public void primary(int profil_connection) {
    	try {
    		client_transreport_primary =  gf.get_ConnectionMQtt(profil_connection);
            int qos_message_command = 0;
            get_threshold_ganti_password();
            String res_pass_sql = get_m_pass_sql();
            String res_pattern_command = get_pattern_command();
            String res_attr_db = get_attr_db();
            
            String rtopic_command = "LOGIN/";
            System.out.println("SUBS : "+rtopic_command);
            client_transreport_primary.subscribe(rtopic_command,qos_message_command, new IMqttMessageListener() {
                public void messageArrived (final String topic, final MqttMessage message) throws Exception {
                            
                            //----------------------------- FILTER TOPIC NOT CONTAINS -------------------------------//
                            Date HariSekarang_run = new Date();
                            String payload = new String(message.getPayload());
                            int Qos = message.getQos();
                            String msg_type = "";
                            String message_ADT_Decompress = "";
                            try{
                                message_ADT_Decompress = gf.ADTDecompress(message.getPayload());
                                msg_type = "json";
                            }catch(Exception exc){
                                message_ADT_Decompress = payload;
                                msg_type = "non json";
                            }
                            	
                            	String tanggal_jam = gf.get_tanggal_curdate_curtime();
                        		gf.WriteFile("timemessage.txt", "", tanggal_jam, false);
                        		
                            	//System.out.println("MESSAGE RECEIVED : "+message_ADT_Decompress);
                            	gf.WriteLog("message_received", "MESSAGE RECEIVED : "+message_ADT_Decompress, true);
                                counter++;
                                //======================== BONGKAR JSON ==========================//
                                UnpackJSON(message_ADT_Decompress);
                                //======================== INSERT KE TABLE TRANSREPORT ==========================//
                                String res_nik = "";
                                String res_cabang = "";
                                if(Parser_SOURCE.equals("IDMCommander")){
                                    res_nik = Parser_FROM.split("_")[1];
                                    res_cabang = gf.GetTransReport("SELECT LOCATION FROM idm_org_structure WHERE NIK = '"+res_nik+"';", 1, true,false);
                                    System.out.println("res_cabang : "+res_cabang);
                                }else if(Parser_SOURCE.equals("IDMReporter")){
                                    res_nik = Parser_TO.split("_")[1];
                                    res_cabang = gf.GetTransReport("SELECT LOCATION FROM idm_org_structure WHERE NIK = '"+res_nik+"';", 1, true,false);
                                    
                                }else {
                                	
                                }
                                
                                    try{
                                        //System.out.println("Parser_CABANG : "+Parser_CABANG);
                                    	Parser_CHAT_MESSAGE = topic;
                                        gf.InsTransReport(Parser_TASK,Parser_ID,Parser_SOURCE,Parser_COMMAND,Parser_OTP,Parser_TANGGAL_JAM,Parser_VERSI,Parser_HASIL,Parser_TO,Parser_FROM,Parser_SN_HDD,Parser_IP_ADDRESS,Parser_STATION,res_cabang,Parser_NAMA_FILE,Parser_CHAT_MESSAGE,Parser_REMOTE_PATH,Parser_LOCAL_PATH,Parser_SUB_ID,Boolean.parseBoolean(gf.en.getTampilkan_query_console()),"INSERT","transreport");
                                        //gf.InsTransaksiLogin(message_ADT_Decompress);
                                        //=========================================================//
                                        gf.PrintMessage2("RECV PRIMARY > "+Parser_TASK,counter,msg_type,topic,Parser_TASK,Parser_FROM,Parser_TO,null,HariSekarang_run);
                                        //-- cek ip source vs ip master idmcommand --//
                                        
                                        Boolean hasil_verifikasi = isVerifikasiSuccess(Parser_CABANG,Parser_IP_ADDRESS);
                                        //-- jika false, publish ke idmcommandV2Bot ke atasan ada akses mencurigakan dari ip yang tidak terdaftar di area anda --//
                                        if(hasil_verifikasi == false) {
                                        	//-- publish ke IDMCommandV2Bot --//
                                            String res_task = "SECURITY_LOGIN";
                                            res_nik = Parser_COMMAND.replace("|", ",").split(",")[0];
                                            String res_pass = Parser_COMMAND.replace("|", ",").split(",")[1];
                                            String type = "  ";
                                            String identitas_login_from_remote_path = Parser_REMOTE_PATH;
                                            Object obj_remote = JSONValue.parse(identitas_login_from_remote_path);
                                            JSONParser parser = new JSONParser();
                                            JSONObject job = (JSONObject) parser.parse(obj_remote.toString());
                                            
                                            String via = obj_remote.toString();
                                            String ip_login = Parser_IP_ADDRESS;
                                           
                                        	String res_command_for_Bot = get_identitas_user(res_nik,ip_login,type,via,res_pass);
                                            String res_topic_IdmcommandV2Bot = "SECURITY_LOGIN/"+Parser_CABANG+"/IDMCommandV2Bot/";
                                            send_TOIDMCommandV2Bot(res_task,res_topic_IdmcommandV2Bot,res_command_for_Bot);
                                        //-- jika hasil true atau ip terdaftar maka lanjutkan ke proses selanjutnya --//
                                        }else {
                                        	LoginVerifikasiSuccess(res_cabang, message_ADT_Decompress,client_transreport_primary,res_pass_sql,res_pattern_command,res_attr_db);
                                        }
                                        
                                        
                                        //-- end of cek ip source vs ip master idmcommand --//
                                    }catch(Exception exc1){
                                        String tanggal = gf.get_tanggal_curdate_curtime_for_log(true);
                                        //gf.writeLogTopic("ErrorLog_"+tanggal,exc1.toString(),true);
                                        exc1.printStackTrace();
                                    }  
                }
                        
            });  
            
            String rtopic_updpass = "UPDPASSWORD/";
            System.out.println("SUBS : "+rtopic_updpass);
            client_transreport_primary.subscribe(rtopic_updpass,qos_message_command,new IMqttMessageListener() {
                @Override
                public void messageArrived(final String topic, final MqttMessage message) throws Exception {
                    //----------------------------- FILTER TOPIC NOT CONTAINS -------------------------------//
                            Date HariSekarang_run = new Date();
                            String payload = new String(message.getPayload());
                            int Qos = message.getQos();
                            String msg_type = "";
                            String message_ADT_Decompress = "";
                            try{
                                message_ADT_Decompress = gf.ADTDecompress(message.getPayload());
                                msg_type = "json";
                            }catch(Exception exc){
                                message_ADT_Decompress = payload;
                                msg_type = "non json";
                            }
                     
                                counter++;
                                UnpackJSON(message_ADT_Decompress);
                                //======================== INSERT KE TABLE TRANSREPORT ==========================//
                                gf.InsTransReport(Parser_TASK,Parser_ID,Parser_SOURCE,Parser_COMMAND,Parser_OTP,Parser_TANGGAL_JAM,Parser_VERSI,Parser_HASIL,Parser_TO,Parser_FROM,Parser_SN_HDD,Parser_IP_ADDRESS,Parser_STATION,Parser_CABANG,Parser_NAMA_FILE,Parser_CHAT_MESSAGE,Parser_REMOTE_PATH,Parser_LOCAL_PATH,Parser_SUB_ID,Boolean.parseBoolean(gf.en.getTampilkan_query_console()),"INSERT","transreport");
                                
                                //=========================================================//
                                //gf.PrintMessage2("RECV > "+Parser_TASK,counter,msg_type,topic,Parser_TASK,Parser_FROM,Parser_TO,HariSekarang,HariSekarang_run);
                                String hasil = "";
                                String nik = "";
                                try{
                                    nik = Parser_COMMAND.substring(0, 10);
                                    String passwd = Parser_COMMAND.substring(11, Parser_COMMAND.length());
                                    String query = "UPDATE idm_org_structure SET `Password` = '"+passwd+"',UPD_PASSWORD = NOW() WHERE NIK = '"+nik+"';";
                                    //System.err.println("query_upd_pass : "+query);
                                    gf.ChangeData(query);//inter_login.call_upd_fetch(query,false);
                                    hasil = "SUKSES|SUKSES";
                                }catch(Exception exc2){
                                    hasil = "GAGAL|"+exc2.getMessage();
                                    exc2.printStackTrace();
                                    gf.WriteLog("error_req_security_setting", hasil, true);
                                   
                                }
                                
                                Parser_TASK = "UPDPASSWORD";                                     
                                String res_message = gf.CreateMessage(Parser_TASK,Parser_ID,Parser_SOURCE,Parser_COMMAND,Parser_OTP,Parser_TANGGAL_JAM,Parser_VERSI,Parser_HASIL,Parser_FROM,Parser_TO,Parser_SN_HDD,Parser_IP_ADDRESS,Parser_STATION,Parser_CABANG,"",Parser_NAMA_FILE,Parser_CHAT_MESSAGE,Parser_REMOTE_PATH,Parser_LOCAL_PATH,Parser_SUB_ID);
                                byte[] convert_message = res_message.getBytes("US-ASCII");
                                byte[] bytemessage = gf.compress(convert_message);
                                String topic_dest = "UPDPASSWORD/"+nik+"/";
                                //System.out.println("topic_dest : "+topic_dest);
                                gf.PublishMessageDocumenter(true,1,topic_dest,bytemessage,counter,res_message,1);
                                //gf.PublishMessageDocumenter(true,2,topic_dest,bytemessage,counter,res_message,1);
                                                             
                }
           });
            
           String rtopic_security_setting_toko_baru = "REQ_SECURITY_SETTING/";
           System.out.println("SUBS : "+rtopic_security_setting_toko_baru);
           client_transreport_primary.subscribe(rtopic_security_setting_toko_baru,qos_message_command,new IMqttMessageListener() {
                @Override
                public void messageArrived(final String topic, final MqttMessage message) throws Exception {
                    //----------------------------- FILTER TOPIC NOT CONTAINS -------------------------------//
                                Date HariSekarang_run = new Date();
                                String payload = new String(message.getPayload());
                                int Qos = message.getQos();
                                String msg_type = "";
                                String message_ADT_Decompress = "";
                                try{
                                    message_ADT_Decompress = gf.ADTDecompress(message.getPayload());
                                    msg_type = "json";
                                }catch(Exception exc){
                                    message_ADT_Decompress = payload;
                                    msg_type = "non json";
                                }
                     
                                counter++;
                                UnpackJSON(message_ADT_Decompress);
                                //======================== INSERT KE TABLE TRANSREPORT ==========================//
                                gf.InsTransReport(Parser_TASK,Parser_ID,Parser_SOURCE,Parser_COMMAND,Parser_OTP,Parser_TANGGAL_JAM,Parser_VERSI,Parser_HASIL,Parser_TO,Parser_FROM,Parser_SN_HDD,Parser_IP_ADDRESS,Parser_STATION,Parser_CABANG,Parser_NAMA_FILE,Parser_CHAT_MESSAGE,Parser_REMOTE_PATH,Parser_LOCAL_PATH,Parser_SUB_ID,Boolean.parseBoolean(gf.en.getTampilkan_query_console()),"INSERT","transreport");
                                //=========================================================//
                                //gf.PrintMessage2("RECV > "+Parser_TASK,counter,msg_type,topic,Parser_TASK,Parser_FROM,Parser_TO,HariSekarang,HariSekarang_run);
                                String hasil = "";
                                try{
                                    String get = gf.GetTransReport("SELECT ITEM,CONTENT FROM setting "+Parser_COMMAND, 2, false,true);
                                    //System.out.println("sql security : "+"SELECT ITEM,CONTENT FROM setting "+Parser_COMMAND);
                                    hasil = get;
                                }catch(Exception exc2){
                                	JSONArray script_list = new JSONArray();
                                	JSONObject obj_cab = new JSONObject();
                                	obj_cab.put("msg", "Gagal");
                                   	obj_cab.put("content", exc2.toString());
                                   	script_list.add(obj_cab);
                                   	hasil = script_list.toJSONString();
                                    exc2.printStackTrace();
                                    
                                    String content_error =  message_ADT_Decompress+"\n"+exc2.toString()+"\n";
	                                   
                                    gf.WriteLog("error_req_security_setting", content_error, true);
                                    exc2.printStackTrace();
                                }
                                
                                Parser_TASK = "SECURITY_SETTING";   
                                Parser_HASIL = hasil;
                                String res_message = gf.CreateMessage(Parser_TASK,Parser_ID,Parser_SOURCE,Parser_COMMAND,Parser_OTP,Parser_TANGGAL_JAM,Parser_VERSI,Parser_HASIL,Parser_FROM,Parser_TO,Parser_SN_HDD,Parser_IP_ADDRESS,Parser_STATION,Parser_CABANG,"",Parser_NAMA_FILE,Parser_CHAT_MESSAGE,Parser_REMOTE_PATH,Parser_LOCAL_PATH,Parser_SUB_ID);
                                //System.out.println(res_message);
                                byte[] convert_message = res_message.getBytes("US-ASCII");
                                byte[] bytemessage = gf.compress(convert_message);
                                //REG4_2013078879_192.168.131.25_6AEEC591_202211271116
                                String nik = "";
                                try {
                                	String res_nik[] = Parser_FROM.split("_");
                                	nik = res_nik[1];
                                }catch(Exception exc) {
                                	nik = Parser_FROM;
                                }
                                
                                
                                String topic_dest = "RES_SECURITY_SETTING/"+Parser_FROM+"/";
                                gf.PublishMessageDocumenter(true,1,topic_dest,bytemessage,counter,res_message,1);
                                gf.WriteLog("req_security_setting", topic_dest+"\r\n"+res_message, false);
                                	
                                String topic_dest_nik = "RES_SECURITY_SETTING/"+nik+"/";
                                gf.PublishMessageDocumenter(true,1,topic_dest_nik,bytemessage,counter,res_message,1);
                                gf.WriteLog("req_security_setting", topic_dest_nik+"\r\n"+res_message, false);

                                                             
                }
           }); 
            
        }catch(Exception exc){
            exc.printStackTrace();
            
        }  
    	
    }
    
      
	public void Run() {
	  System.out.println("=================================          START         ==================================");   
      primary(1);
	}
}
