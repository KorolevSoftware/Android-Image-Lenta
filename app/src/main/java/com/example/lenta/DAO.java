package com.example.lenta;

import java.util.List;

public interface DAO <T>
{
    List<T> getDataByPage(int page, int itemInPage);
}