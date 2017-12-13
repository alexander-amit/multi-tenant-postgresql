package amit.sap.multitenant;

import java.math.BigInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import amit.sap.multitenant.config.TenantProvider;

@RestController
@RequestMapping("/heros")
public class HeroController {

	@Autowired
	TenantProvider tenantProvider;

	@Autowired
	HeroRepository heroRepository;

	@RequestMapping("")
	Iterable<Hero> getAll() {
		return heroRepository.findAll();
	}

	@RequestMapping(path = "/{tenantId}", method = RequestMethod.POST)
	Hero create(@RequestBody Hero hero,@PathVariable String tenantId) {
		tenantProvider.setTenantId(tenantId);
		
		return heroRepository.save(hero);
	}

	@RequestMapping("/{id}")
	Hero get(@PathVariable Long id) {
		return heroRepository.findOne(id);
	}

	@RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
	void delete(@PathVariable Long id) {
		heroRepository.delete(id);
	}
}
