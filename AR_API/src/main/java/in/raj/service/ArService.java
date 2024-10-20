package in.raj.service;

import in.raj.binding.App;
import java.util.List;

//4
public interface ArService {
    public String createApplication(App app);

    public List<App> fetchApps(Integer userId);
}
