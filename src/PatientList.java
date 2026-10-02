import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PatientList {
    private Patient[] patientArray;
    private final int maxPatients=1000;
    private int nextIndex=0;

    public PatientList(){
        patientArray=new Patient[maxPatients];
    }
    public boolean add(Patient pat){
        return addOrdered(pat);
    }
    private boolean addOrdered(Patient pat){
        if (nextIndex>=patientArray.length){return false;}
        int currentIndex=nextIndex-1;
        while(currentIndex>=0&&patientArray[currentIndex].getPatientIdentity().isLessThan(pat.getPatientIdentity()) == false && patientArray[currentIndex].getPatientIdentity().match(pat.getPatientIdentity()) == false){
            patientArray[currentIndex+1]=patientArray[currentIndex];
            currentIndex--;
        }
        patientArray[currentIndex+1]=pat;
        nextIndex++;
        return true;
    }

    public Patient find(PatientIdentity ID){
        return binarySearch(ID);

    }
    private Patient binarySearch(PatientIdentity ID){
        int lower=0;
        int upper=nextIndex-1;

        while(upper>=lower){
            int mid=(upper+lower)/2;
            PatientIdentity midID=patientArray[mid].getPatientIdentity();
            if(midID.match(ID)){
                return patientArray[mid];
            }
            if(midID.isLessThan(ID)){
                lower=mid+1;
            }
            else{
                upper=mid-1;
            }
        }
        return null;
    }

    public class Iterator{
        private int index;
        public Iterator(){
            index=0;
        }
        public Patient next(){
            if((index >= nextIndex) || (patientArray[index] == null)){
                return null;
            }
            Patient result=patientArray[index];
            index++;
            return result;
        }
        public Patient peek(){
            if(index>=nextIndex){
                return null;
            }
            return patientArray[index];

        }
    }

    public boolean saveToFile(String filename){
        boolean r=true;
        File file=new File(filename);
        FileWriter writer=null;
        try{
            writer=new FileWriter(file);
            Iterator iter =new Iterator();
            while ((iter.peek()!=null)){
                Patient pat=iter.next();
                writer.write(pat.toCSV()+"\n");
            }
            writer.close();
        }
        catch (IOException e){
            e.printStackTrace();
            r=false;
        }
        return r;

    }

    public boolean importFile(String filename){
        boolean r=true;
        Patient[] b=new Patient[maxPatients];
        int bCount=0;
        BufferedReader reader=null;
        try {
            reader = new BufferedReader(new FileReader(new File(filename)));
            String line;
            while ((line = reader.readLine()) != null) {
                Patient pat = parseLine(line);
                if (pat != null && bCount < b.length) {
                    b[bCount] = pat;
                    bCount++;
                }
            }
            reader.close();
        }catch (IOException e){
            e.printStackTrace();
            r=false;
        }
        if(r){
            mergeSort(b,0,bCount-1);
            mergeIntoList(b,bCount);
        }
        return r;
    }
    private Patient parseLine(String l){
        try{
            String[] parts=l.split(",");
            if(parts.length!=3){return null;}
            String last=parts[0].trim();
            String first=parts[1].trim();
            SimpleDateFormat dateFormat=new SimpleDateFormat("MM-dd-yyyy");
            Date dob=dateFormat.parse(parts[2].trim());
            Name name=new StandardName(first,last);
            return new Patient(new PatientIdentity(name,dob));
        }catch (Exception e){
            return null;
        }
    }
    private void mergeSort(Patient[] arr,int left, int right){
        if(left<right){
            int mid=(left+right)/2;
            mergeSort(arr,left,mid);
            mergeSort(arr,mid+1,right);
            merge(arr,left,mid,right);
        }
    }
    private void merge(Patient[] arr,int left,int mid,int right){
        int n1=mid-left+1;
        int n2=right-mid;
        Patient[] l=new Patient[n1];
        Patient[] r=new Patient[n2];
        for(int i=0;i<n1;i++){l[i]=arr[left+i];}
        for(int j=0;j<n2;j++){r[j]=arr[mid+1+j];}
        int i=0;
        int j=0;
        int k=left;
        while(i<n1&&j<n2){
            PatientIdentity idL=l[i].getPatientIdentity();
            PatientIdentity idR=r[j].getPatientIdentity();
            if(idL.isLessThan(idR)||idL.match(idR)){
                arr[k]=l[i];
                i++;
            }
            else{
                arr[k]=r[j];
                j++;
            }
            k++;
        }
        while(i<n1){
            arr[k]=l[i];
            i++;
            k++;
        }
        while(j<n2){
            arr[k]=r[j];
            j++;
            k++;
        }
    }

    private void mergeIntoList(Patient[] sortedBuffer,int bufferC){
        Patient[] merged=new Patient[maxPatients];
        int i=0;
        int j=0;
        int k=0;
        while(i<nextIndex&&j<bufferC&&k<maxPatients){
            PatientIdentity idA=patientArray[i].getPatientIdentity();
            PatientIdentity idB=sortedBuffer[j].getPatientIdentity();
            if(idA.isLessThan(idB)|| idA.match(idB)){
                merged[k]=patientArray[i];
                i++;
            }
            else{
                merged[k]=sortedBuffer[j];
                j++;
            }
            k++;
        }
        while(i<nextIndex&&k<maxPatients){
            merged[k]=patientArray[i];
            i++;
            k++;
        }
        while (j<bufferC&&k<maxPatients){
            merged[k]=sortedBuffer[j];
            j++;
            k++;
        }
        patientArray=merged;
        nextIndex=k;
    }

}