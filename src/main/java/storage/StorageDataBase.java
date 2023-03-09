package storage;

import lombok.Getter;

public abstract class StorageDataBase implements Storage {
    @Getter
    private final String connectionType = "type";
}
