package hu.bme.mit.spaceship;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

public class GT4500Test {

  private GT4500 ship;
  private TorpedoStore ts1;
  private TorpedoStore ts2;

  @BeforeEach
  public void init(){
    ts1 = mock(TorpedoStore.class);
    ts2 = mock(TorpedoStore.class);

    this.ship = new GT4500(ts1, ts2);
  }

  @Test
  public void fireTorpedo_Single_Success(){
    // Arrange
    when(ts1.fire(anyInt())).thenReturn(true);
    when(ts2.fire(anyInt())).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(true, result);
    verify(ts1, times(1)).fire(anyInt());
    verify(ts2, times(0)).fire(anyInt());
  }

  @Test
  public void fireTorpedo_All_Success(){
    // Arrange
    when(ts1.fire(anyInt())).thenReturn(true);
    when(ts2.fire(anyInt())).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.ALL);

    // Assert
    assertEquals(true, result);
    verify(ts1, times(1)).fire(anyInt());
    verify(ts2, times(1)).fire(anyInt());
  }

  @Test
  public void fireTorpedo_Single_Success_with_Emty_Secondary(){
    // Arrange
    when(ts1.fire(anyInt())).thenReturn(true);
    when(ts2.fire(anyInt())).thenReturn(false);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(true, result);
    verify(ts1, times(1)).fire(anyInt());
    verify(ts2, times(0)).fire(anyInt());
  }

  @Test
  public void fireTorpedo_Single_Fail(){
    // Arrange
    when(ts1.fire(anyInt())).thenReturn(false);
    when(ts2.fire(anyInt())).thenReturn(false);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(false, result);
    verify(ts1, times(1)).fire(anyInt());
    verify(ts2, times(0)).fire(anyInt());
  }

  @Test
  public void fireTorpedo_Single_Fail_with_Working_Secondary(){
    // Arrange
    when(ts1.fire(anyInt())).thenReturn(false);
    when(ts2.fire(anyInt())).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(false, result);
    verify(ts1, times(1)).fire(anyInt());
    verify(ts2, times(0)).fire(anyInt());
  }

  @Test
  public void fireTorpedo_All_Fail_Both(){
    // Arrange
    when(ts1.fire(anyInt())).thenReturn(false);
    when(ts2.fire(anyInt())).thenReturn(false);
    when(ts1.isEmpty()).thenReturn(true);
    when(ts2.isEmpty()).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.ALL);

    // Assert
    assertEquals(false, result);
    verify(ts1, times(0)).fire(anyInt());
    verify(ts2, times(0)).fire(anyInt());
  }

  @Test
  public void fireTorpedo_All_Fail_One(){
    // Arrange
    when(ts1.fire(anyInt())).thenReturn(true);
    when(ts2.fire(anyInt())).thenReturn(false);
    when(ts1.isEmpty()).thenReturn(false);
    when(ts2.isEmpty()).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.ALL);

    // Assert
    assertEquals(false, result);
    verify(ts1, times(0)).fire(anyInt());
    verify(ts2, times(0)).fire(anyInt());
  }

  @Test
  public void fireTorpedo_All_Fail_Secondary(){
    // Arrange
    when(ts1.fire(anyInt())).thenReturn(false);
    when(ts2.fire(anyInt())).thenReturn(true);
    when(ts1.isEmpty()).thenReturn(true);
    when(ts2.isEmpty()).thenReturn(false);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.ALL);

    // Assert
    assertEquals(true, result);
    verify(ts1, times(0)).fire(anyInt());
    verify(ts2, times(1)).fire(anyInt());
  }

  @Test
  public void FillerTest(){

    // Act
    GT4500 ship = new GT4500();
    boolean laser = ship.fireLaser(FiringMode.ALL);

    // Assert
    assertEquals(false, laser);
  }

  @Test
  public void fireTorpedo_Single_Success_Secondary(){
    // Arrange
    when(ts1.fire(anyInt())).thenReturn(false);
    when(ts2.fire(anyInt())).thenReturn(true);
    ship.fireTorpedo(FiringMode.SINGLE);
    when(ts1.isEmpty()).thenReturn(true);
    when(ts2.isEmpty()).thenReturn(false);

    // Act
    ship.fireTorpedo(FiringMode.SINGLE);
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(true, result);
    verify(ts1, times(1)).fire(anyInt());
    verify(ts2, times(2)).fire(anyInt());
  }


  @Test
  public void fireTorpedo_Single_Success_Secondary_Empty(){
    // Arrange
    when(ts1.fire(anyInt())).thenReturn(true);
    when(ts2.fire(anyInt())).thenReturn(false);
    when(ts1.isEmpty()).thenReturn(false);
    when(ts2.isEmpty()).thenReturn(true);
    ship.fireTorpedo(FiringMode.SINGLE);

    // Act
    ship.fireTorpedo(FiringMode.SINGLE);
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(true, result);
    verify(ts1, times(3)).fire(anyInt());
    verify(ts2, times(0)).fire(anyInt());
  }

  @Test
  public void fireTorpedo_Single_Success_Primary_Empty(){
    // Arrange
    when(ts1.fire(anyInt())).thenReturn(false);
    when(ts2.fire(anyInt())).thenReturn(true);
    when(ts1.isEmpty()).thenReturn(true);
    when(ts2.isEmpty()).thenReturn(false);

    // Act
    ship.fireTorpedo(FiringMode.SINGLE);
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(true, result);
    verify(ts1, times(0)).fire(anyInt());
    verify(ts2, times(2)).fire(anyInt());
  }
}
