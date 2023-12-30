package data;

final public class Password {

    String supportPassword;
    public Password(String supportPassword){

        if (supportPassword == null) {
            throw new NullPointerException("Password cannot be null");
        }

        this.supportPassword = supportPassword;
    }

    public String getSupportPassword() {
        return supportPassword;
    }

    @Override
    public boolean equals (Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Password pw = (Password) o;
        return supportPassword.equals(pw.supportPassword);
    }

    @Override
    public int hashCode () { return supportPassword.hashCode(); }
}
