package evoting.biometricdataperipheral;

import data.BiometricData;
import data.Nif;
import data.SingleBiometricData;
import exception.NifFormatException;
import exception.NotValidPassportException;
import exception.PassportBiometricReadingException;

public class PassportBiometricReaderImpl implements PassportBiometricReader{

    private boolean isValid = true;
    private boolean errorAtGetBioData = false;
    @Override
    public void validatePassport () throws NotValidPassportException{
        if(!isValid){
            throw new NotValidPassportException("Not valid passport");
        }
    }
    @Override
    public Nif getNifWithOCR (){
        //Es para que no de error de compilacion
        try {
            return new Nif("12345678H");
        } catch (NifFormatException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public BiometricData getPassportBiometricData () throws PassportBiometricReadingException {
        byte[] facialData = {5,15,25,35};
        byte[] fingerData = {10,20,30,40};

        if(!errorAtGetBioData){
            return new BiometricData(new SingleBiometricData(facialData), new SingleBiometricData(fingerData));

        }
        else {
            throw new PassportBiometricReadingException("Something went wrong while reading passport's biometric data");
        }
    }
}
