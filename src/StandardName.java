public class StandardName extends Name{
    private String first;
    private String last;
    public StandardName(String first, String last){
        this.first=first;
        this.last=last;
    }



    @Override
    public String fullname(){
        return last+", "+first;
    }



    @Override
    protected String sortKey(){
        return last.toLowerCase()+","+first.toLowerCase();
    }
}
