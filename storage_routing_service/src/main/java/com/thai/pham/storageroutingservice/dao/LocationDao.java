package thai.pham.storageroutingservice.dao;

@Repository
public class LocationDao implements ReadOnlyLocationRepository, ReadWriteLocationRepository {
    private ReadOnlyLocationRepository readOnlyRepository;
    private ReadWriteLocationRepository readWriteRepository;

    @Autowired
    public LocationDao(ReadOnlyLocationRepository readOnlyRepository, ReadWriteLocationRepository readWriteRepository) {
        this.readOnlyRepository = readOnlyRepository;
        this.readWriteRepository = readWriteRepository;
    }

    @Override
    public List<Location> findAll() {
        return readOnlyRepository.findAll();
    }

    @Override
    public void delete(Location location) {
        readWriteRepository.delete(location);
    }

    @Override
    public void deleteAll() {
        readWriteRepository.deleteAll();
    }

    @Override
    public <S extends Location> S save(S s) {
        return readWriteRepository.save(s);
    }

    @Override
    public void flush() {
        readWriteRepository.flush();
    }

    @Override
    public <S extends Location> S saveAndFlush(S s) {
        return readWriteRepository.saveAndFlush(s);
    }
}