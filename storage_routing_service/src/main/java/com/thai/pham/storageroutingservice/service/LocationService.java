package thai.pham.storageroutingservice.service;

@Service
public class LocationService {
    private LocationRepository locationRepo;

    @Autowired
    public LocationService(LocationRepository locationRepo) {
        this.locationRepo = locationRepo;
    }

    public List<Location> getAllLocation() {
        return locationRepo.getAll();
    }
}