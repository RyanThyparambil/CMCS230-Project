public abstract class Name {
    public abstract String fullName();
    protected abstract String sortKey();
    public boolean match(Name other){
        return this.fullName().toLowerCase().equals(other.fullName().toLowerCase());
    }
    public boolean isLessThan(Name other){
        return this.sortKey().compareTo(other.sortKey())<0;
    }

    @Override
    public String toString(){return fullName();}


}
