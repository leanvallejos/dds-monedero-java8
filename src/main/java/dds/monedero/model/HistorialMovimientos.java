package dds.monedero.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class HistorialMovimientos {
  private List<Movimiento> movimientos = new ArrayList<>();

  public long cantidadDepositosDeHoy(){
    return this.movimientos.stream().filter(movimiento -> movimiento.isDeposito() && movimiento.esDeLaFecha(LocalDate.now())).count();
  }

  public double montoExtraidoElDia(LocalDate dia){

   return  this.movimientos.stream()
        .filter(movimiento -> !movimiento.isDeposito() && movimiento.esDeLaFecha(dia))
        .mapToDouble(Movimiento::getMonto)
        .sum();
  }

  public void agregar(Movimiento movimiento){
    movimientos.add(movimiento);
  }
  public HistorialMovimientos() {
  }

  public List<Movimiento> getMovimientos() {
    return movimientos;
  }
}
