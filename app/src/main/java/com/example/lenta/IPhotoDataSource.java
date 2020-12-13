package com.example.lenta;

import java.util.List;

public interface IPhotoDataSource<T> {
    List<T> getDataByPage(int pageNumber, int pageSize);
    void setSearchTile(String tile);
}
