import java.util.Scanner;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;

public class MainClass{   
    
    //elit uyeye ait ozellikler ve yapici metotu
    public class elitUye extends ortak{
        public elitUye(String ad, String soyad,String email){
           super(ad,soyad,email);
           id =1; //gmail atarken ozel uyeleri ayırabilmek icin selector
        }
       }
    
       public class ortak{
        //genel ve elit uyenin ortak ozellikleri
        public int id;
        public String ad,soyad,email;
        public ortak(String ad, String soyad, String email){
            this.ad = ad;
            this.soyad = soyad;
            this.email = email;
        } 
    } 
    
        //genel uyeye ait ozellikler ve yapici metotu
        public class genelUye extends ortak{
            public genelUye(String ad, String soyad,String email){
                super(ad,soyad,email); //kalıtımla aktarılan ozellikler
                id =2; //gmail atarken genel uyeleri ayırabilmek icin selector
             }
        }

public static void main(String[] args) throws Exception{

    //scanner sınıfından nesne olusturulması
    Scanner scan = new Scanner(System.in);
    Scanner scan2 = new Scanner(System.in);

    //file dosyası secilmesi
    File file = new File("kullanıcılar.txt");
        if (!file.exists()) {
            file.createNewFile();
        }
    
    
    //file i/o islemleri icin gerekli siniflardan nesneler olusturulması
    FileWriter fileWriter = new FileWriter(file, true);
    BufferedWriter bWriter = new BufferedWriter(fileWriter);
    FileReader fileRead = new FileReader(file);
    BufferedReader bufferreader = new BufferedReader(fileRead);
    

    //kullanılcak main sınıf degiskenleri ve nesneleri
    int inp;
    String ad,soyad,email,eklencek,line;
    elitUye e1 ;
    genelUye g1;
    //ilk menu
    do{
        helper.EkleMenu();
        inp = scan.nextInt();
    }while(inp<0 && inp>4);
    
    //menu1 islemler
    if(inp==1){
        //elit uye ekleme
        System.out.println("Sirasiyla isim soyisim email giriniz");
        ad = scan2.nextLine();
        soyad = scan2.nextLine();
        email = scan2.nextLine();
        eklencek = ad+" "+soyad+"   "+email;

        //ekleme islemi en sona yapildigi icin ustteki bilginin silinmesi icin islemler
        int satir =0;

        //okuma dongusu
        while((line = bufferreader.readLine()) != null){
            satir++;
            if(satir==1){
                line+=("\n"+eklencek);               
            } 
            bWriter.newLine();
            bWriter.write(line);
        }
            bWriter.close();    
        int fazla = 0; //file i/o satir sonuna ekledigi icin eski satir sayisi hesaplayan degisken
        while(bufferreader.readLine() != null){
            fazla++;
        } 
        
        //fazla satirlarin silinmesi
         for(int i=1;i<fazla;i++){
            helper.removeRecord("kullanıcılar.txt", 1);
        }
        
      
    }
    else if(inp==2){
        //genel uye ekleme
        System.out.println("Sirasiyla isim soyisim email giriniz");
        ad = scan2.nextLine();
        soyad = scan2.nextLine();
        email = scan2.nextLine();
        eklencek = ad+" "+soyad+"   "+email;
        bWriter.write(eklencek);
        bWriter.newLine();
        bWriter.close();
    }

    else{
        //mail yollama secenegi
        helper.MailMenu();

    }
        //acik kalan nesnelerin en sonda kapatilmasi
        bufferreader.close();
        scan.close();
        scan2.close();
       
    }
    

    //kullanilcak metotlarin tanimlandigi helper sinifi
    public class helper{
        //ekleme menu fonk
        public static void EkleMenu(){
            System.out.println("1- Elit uye ekle");
            System.out.println("2- Genel uye ekle");
            System.out.println("3- Mail gonder");
        }
        
        //mail atma menu fonk
        public static void MailMenu(){
            System.out.println("1- Elit uyelere mail gonder");
            System.out.println("2- Genel uyelere mail gonder");
            System.out.println("3- Tum uyelere mail gonder");
        }
    
        //metinden satir silme fonk
        public static void removeRecord(String filepath, int deleteline)
        {
            //aktarilcak dosyanın olsturulması
            String tempFile = "temp.txt";
            File oldFile = new File(filepath);
            File newFile = new File(tempFile);
    
            //istenmeyen satiri bulcak olan dongu degiskeni ve gecerli satir
            int line = 0;
            String currentLine;
    
            try
            {
                //fonksiyonda kullanilcak nesneler
                FileWriter fw = new FileWriter(tempFile,true);
                BufferedWriter bw = new BufferedWriter(fw);
                PrintWriter pw = new PrintWriter(bw);
    
                FileReader fr = new FileReader(filepath);
                BufferedReader br = new BufferedReader(fr);
    
                //istenmeyen satırı bulan dongu
                while((currentLine = br.readLine()) !=null)
                {
                    line++;
                    if (deleteline != line)
                    {
                        pw.println(currentLine);
                    }
                }
                //fonksiyonda kullanilan nesnelerin kapatilmasi
                pw.flush();
                pw.close();
                fr.close();
                br.close();
                bw.close();
                fw.close();
    
                //eski dosyanın silinmesi ve yeni dosyanın adının degistirilmesi
                oldFile.delete();
                File dump = new File(filepath);
                newFile.renameTo(dump);
            }
            catch(Exception e){
                System.out.println(e);
            }
        }
    
        
    }
    
    
}

