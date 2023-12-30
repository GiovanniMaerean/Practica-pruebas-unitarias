package data;

final public class SingleBiometricData {
    byte[] data;

    public SingleBiometricData(byte[] biometricData) throws NullPointerException{
        if (biometricData == null) {
            throw new NullPointerException("Single biometric data cannot be null");
        }
        this.data = biometricData;
    }

    public byte[] getBiometricData() {
        return data;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SingleBiometricData sinBioData = (SingleBiometricData) o;
        return data == sinBioData.data;
    }
}
