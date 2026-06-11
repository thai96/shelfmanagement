package thai.pham.storageroutingservice.service;

@Service
public class LocationService {
    private LocationRepository locationRepo;

    @Autowired
    public LocationService(LocationRepository locationRepo) {
        this.locationRepo = locationRepo;
    }

    public List<Location> getAllLocation() {
        return locationRepo.findAll();
    }

    public Page<Location> getWareHouseLocation(Pageable pageable){
        return locationRepo.findLocationByLocationType(LocationType.WARE_HOUSE);
    }

    public Page<Location> getStoreLocation(Pageable pageable){
        return locationRepo.findLocationByLocationType(LocationType.STORE);
    }

    public Page<Location> getLocationByName(String input, Pageable pageable) {
        return locationRepo.findLocationByNameContaining(input, pageable);
    }
}