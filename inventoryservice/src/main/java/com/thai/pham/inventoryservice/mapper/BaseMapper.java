package com.thai.pham.inventoryservice.mapper;

public interface BaseMapper<Entity, MappedObject> {
    public Entity mapEntity(MappedObject mappedObject);

    public MappedObject mapObject(Entity entity);
}
