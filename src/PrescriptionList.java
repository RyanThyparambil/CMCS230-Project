public class PrescriptionList {
    private ListRecord h;
    private ListRecord c;
    public PrescriptionList(){
        h=null;
        c=null;
    }
    public void add(Prescription p){
        ListRecord newRecord=new ListRecord(p);
        if(h==null||(comesBefore(p,h.data))){
            newRecord.next=h;
            h=newRecord;
            return;
        }
        ListRecord pBefore=h;
        while(pBefore.next!=null&&!(comesBefore(p,pBefore.next.data))){
            pBefore=pBefore.next;
        }
        newRecord.next=pBefore.next;
        pBefore.next=newRecord;
    }

    private static boolean comesBefore(Prescription p1,Prescription p2){
        return p1.getDateOfIssue().compareTo(p2.getDateOfIssue())>0;
    }

    public void init(){
        c=h;
    }
    public Prescription next(){
        if(c==null){return null;}
        Prescription result=c.data;
        c=c.next;
        return result;
    }

    private class ListRecord{
        public Prescription data;
        public ListRecord next;
        public ListRecord(Prescription p){
            data=p;
            next=null;
        }
    }
}