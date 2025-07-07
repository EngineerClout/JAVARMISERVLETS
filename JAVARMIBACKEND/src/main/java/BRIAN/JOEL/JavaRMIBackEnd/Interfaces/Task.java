package BRIAN.JOEL.JavaRMIBackEnd.Interfaces;

import java.io.Serializable;

public interface Task<T> extends Serializable {
    T execute();
}