package thai.pham.storageroutingservice.service;

@Service
public class LocationService {
    private LocationDao locationDao;

    @Autowired
    public LocationService(LocationDao locationDao) {
        this.locationDao = locationDao;
    }

    public List<Location> getAllLocation() {
        return locationDao.getAll();
    }
}