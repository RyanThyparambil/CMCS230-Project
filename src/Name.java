public abstract class Name {
    public abstract String fullName();
    protected abstract String sortKey();
    public boolean match(Name other){
        return this.fullName().toLowerCase().equals(other.fullName().toLowerCase());
    }


}
