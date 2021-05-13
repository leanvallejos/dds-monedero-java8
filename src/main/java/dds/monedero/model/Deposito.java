package dds.monedero.model;

public class Deposito implements  TipoMovimiento{

  public double calculoParaSaldo(double cuanto){
    return cuanto;
  }

  public  boolean deposito(){
    return true;
  }
}
