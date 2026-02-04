package com.cinebook.movieservice.config;

import com.cinebook.movieservice.entity.Cinema;
import com.cinebook.movieservice.entity.City;
import com.cinebook.movieservice.repository.CinemaRepository;
import com.cinebook.movieservice.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Component
public class LocationDataInitializer implements CommandLineRunner {

    @Autowired
    private CityRepository cityRepo;

    @Autowired
    private CinemaRepository cinemaRepo;

    @Override
    @Transactional
    public void run(String... args) {
        if (cityRepo.count() > 0) return; 

        System.out.println("🇮🇳 Seeding ALL India Data...");

        // 1. Create Cities
        createCity("Mumbai", "Gateway of India Icon");
        createCity("Delhi-NCR", "India Gate Icon");
        createCity("Bengaluru", "Vidhana Soudha Icon");
        createCity("Hyderabad", "Charminar Icon");
        createCity("Ahmedabad", "Teen Darwaza Icon");
        createCity("Chennai", "Temple Icon");
        createCity("Kolkata", "Victoria Memorial Icon");
        createCity("Pune", "Shaniwar Wada Icon");

        // 2. Populate Mumbai Cinemas (FULL LIST FROM SCREENSHOTS)
        City mumbai = cityRepo.findByName("Mumbai");
        if (mumbai != null) {
            List<String[]> mumbaiCinemas = Arrays.asList(
                // SOUTH MUMBAI & CENTRAL
                new String[]{"Sterling Cineplex", "Fort, Azad Maidan"},
                new String[]{"Regal Cinema", "Colaba, Apollo Bunder"},
                new String[]{"EROS INOX IMAX", "Churchgate"},
                new String[]{"Mukta A2: New Excelsior", "Fort, Near VT Station"},
                new String[]{"INOX Laserplex: CR2", "Nariman Point"},
                new String[]{"Metro INOX Cinemas", "Marine Lines"},
                new String[]{"Maratha Mandir", "Mumbai Central"},
                new String[]{"Nishat Cinema", "Grant Road"},
                new String[]{"Chitra Cinema", "Dadar (East)"},
                new String[]{"Plaza Cinema", "Dadar (West)"},
                new String[]{"Gold Cinema: Dadar", "Dadar (East)"},
                new String[]{"INOX: Nakshatra Mall", "Dadar (West)"},
                new String[]{"Citylight Cinema", "Mahim"},
                new String[]{"Movietime Star City", "Matunga (West)"},
                new String[]{"PVR ICON: Phoenix Palladium", "Lower Parel"},
                new String[]{"Mukta A2: Jai Hind", "Lalbaugh"},
                new String[]{"MovieMax: Sion", "Sion Circle"},
                new String[]{"Miraj Cinemas: IMAX", "Wadala"},

                // BANDRA TO ANDHERI
                new String[]{"G7 Multiplex", "Bandra (West)"},
                new String[]{"PVR: Le Reve-Globus Mall", "Bandra (West)"},
                new String[]{"Movietime Suburbia", "Bandra (West)"},
                new String[]{"Mukta A2: Orion", "Santacruz (East)"},
                new String[]{"Gold Cinema: Santacruz", "Santacruz (West)"},
                new String[]{"PVR: Lido", "Juhu, Santa Cruz (W)"},
                new String[]{"PVR: Dynamix Mall", "Juhu"},
                new String[]{"Maison INOX: Jio World Plaza", "BKC"},
                new String[]{"Maison PVR: Jio World Drive", "BKC"},
                new String[]{"Bahar Cinema", "Vile Parle (East)"},
                new String[]{"Mukta A2: SunCity", "Vile Parle (East)"},
                new String[]{"PVR ICON: Infiniti", "Andheri (West)"},
                new String[]{"PVR: Citi Mall", "Andheri (West)"},
                new String[]{"PVR: C&B Square Chakala", "Andheri (East)"},
                new String[]{"MovieMax: Andheri", "Andheri-Kurla Road"},
                new String[]{"Fun Republic: Cinepolis", "Andheri (West)"},

                // MALAD TO BORIVALI
                new String[]{"PVR: Infiniti", "Malad (West)"},
                new String[]{"INOX: Megaplex Inorbit", "Malad (West)"},
                new String[]{"MOVIETIME: Malad", "Malad (West)"},
                new String[]{"Kasturba Cinema", "Malad (West)"},
                new String[]{"Gold Cinema: Malad", "Malad"},
                new String[]{"Topiwala Mukta A2", "Goregaon (West)"},
                new String[]{"HDFC Millennia PVR ICON", "Oberoi Mall, Goregaon"},
                new String[]{"MOVIE TIME: HUB", "Goregaon (East)"},
                new String[]{"Miraj Cinemas: Anupam", "Goregaon (East)"},
                new String[]{"INOX: Raghuleela Mall", "Kandivali (West)"},
                new String[]{"PVR: Milap", "Kandivali (West)"},
                new String[]{"Maxus Cinemas", "Kandivali (East)"},
                new String[]{"INOX Megaplex: Sky City", "Borivali (East)"},
                new String[]{"Ajanta Cinema Cinex", "Borivali (West)"},
                new String[]{"Gold Cinema: Sona", "Borivali (East)"},
                new String[]{"Maxus Cinemas", "Borivali (West)"},
                new String[]{"Movietime: Dahisar", "Dahisar (East)"},
                new String[]{"INOX: Thakur Mall", "Dahisar"},

                // THANE & BEYOND
                new String[]{"Cinepolis: Lake Shore", "Thane (Viviana Mall)"},
                new String[]{"Cinepolis: VIP Lake Shore", "Thane (Viviana Mall)"},
                new String[]{"INOX: Korum Mall", "Thane (West)"},
                new String[]{"Anand Cinema", "Thane (East)"},
                new String[]{"Gold Cinema: Shivaji Road", "Thane (West)"},
                new String[]{"MovieMax: Wonder Mall", "Thane"},
                new String[]{"MovieMax: Eternity Mall", "Thane"},
                new String[]{"Cinepolis: High Street", "Thane"},
                new String[]{"Miraj Cinemas: R Mall", "Mulund"},
                new String[]{"Devgn Cinex", "Mulund"},
                new String[]{"PVR: Market City", "Kurla (West)"},
                new String[]{"Bharat Cineplex", "Kurla (West)"},
                new String[]{"INOX: R-City", "Ghatkopar (West)"},
                new String[]{"PVR: Odeon Mall", "Ghatkopar"},
                new String[]{"Movietime Cubic Mall", "Chembur"},

                // NAVI MUMBAI
                new String[]{"Cinepolis: Nexus Seawoods", "Nerul"},
                new String[]{"INOX: Palm Beach", "Vashi"},
                new String[]{"INOX: Raghuleela Mall", "Vashi"},
                new String[]{"Cinepolis: Aurum", "Ghansoli"},
                new String[]{"Fun Square Cinema", "Sanpada"},
                new String[]{"BMX Cinemas", "Littleworld Kharghar"},
                new String[]{"K.K. Cinema", "Kamothe"},
                new String[]{"PVR: Orion Mall", "Panvel"},
                new String[]{"Miraj Cinemas: Cineraj", "Panvel"},

                // KALYAN / DOMBIVALI / AMBERNATH / BADLAPUR
                new String[]{"MovieMax: SM5", "Kalyan"},
                new String[]{"Mukta A2: Triveni Grande", "Kalyan"},
                new String[]{"INOX: Metro Junction", "Kalyan"},
                new String[]{"Miraj Cinemas: Dombivali", "Dombivali (East)"},
                new String[]{"Tilak Cineplex", "Dombivali"},
                new String[]{"Gopi Cinema", "Dombivali"},
                new String[]{"PVR: Lodha Xperia", "Palava"},
                new String[]{"Funcity Big Cinemas", "Ulhasnagar"},
                new String[]{"Miraj Cinemas: Ashok", "Ulhasnagar"},
                new String[]{"Aman Theatre", "Ulhasnagar"},
                new String[]{"Venus Cinema", "Ulhasnagar"},
                new String[]{"BMX Cinemas", "Ambernath"},
                new String[]{"Miraj Cinemas: Star", "Ambernath"},
                new String[]{"Vaishali Cinema", "Badlapur"},
                new String[]{"PVR: Haseen", "Bhiwandi"},
                new String[]{"Nazrana Cinema", "Bhiwandi"},

                // VASAI - VIRAR
                new String[]{"Rassaz Multiplex", "Mira Road"},
                new String[]{"MovieMax: Mira Road", "Mira Road"},
                new String[]{"Maxus Cinemas", "Bhayander"},
                new String[]{"Miraj Cinemas: Dattani", "Vasai"},
                new String[]{"KT Vision Cinema", "Vasai"},
                new String[]{"K Movie Star", "Vasai (West)"},
                new String[]{"PVR: The Capital Mall", "Nalasopara"},
                new String[]{"Miraj Cinemas: Funfiesta", "Nalasopara"},
                new String[]{"Woodland Cinemas", "Virar"},
                new String[]{"Rockstar Nova Cinemaz", "Virar"},
                new String[]{"Movie Max V", "Virar"}
            );

            for (String[] data : mumbaiCinemas) {
                Cinema c = new Cinema();
                c.setName(data[0]);
                c.setAddress(data[1]);
                c.setType("Multiplex");
                c.setCity(mumbai);
                cinemaRepo.save(c);
            }
            System.out.println("✅ Loaded " + mumbaiCinemas.size() + " Cinemas for Mumbai.");
         // 3. Populate Delhi-NCR Cinemas (From Screenshots)
            City delhi = cityRepo.findByName("Delhi-NCR");
            if (delhi != null) {
                List<String[]> delhiCinemas = Arrays.asList(
                    // DELHI & CENTRAL
                    new String[]{"PVR: Select City Walk", "Saket, District Centre"},
                    new String[]{"PVR: Promenade", "Vasant Kunj, Nelson Mandela Road"},
                    new String[]{"PVR IMAX with Laser, Priya", "Vasant Vihar, Basant Lok"},
                    new String[]{"PVR Director's Cut", "Ambience Mall, Vasant Kunj"},
                    new String[]{"Cinepolis: DLF Avenue", "Saket District Centre"},
                    new String[]{"INOX: Laserplex, Nehru Place", "Epicuria, TDI South Bridge"},
                    new String[]{"INOX: COCA-COLA IMAX Paras", "Nehru Place"},
                    new String[]{"PVR: Plaza", "Connaught Place, Outer Circle"},
                    new String[]{"INOX: Odeon", "Connaught Place"},
                    new String[]{"Delite Cinema", "Asaf Ali Road, Daryaganj"},
                    new String[]{"Liberty Cinema", "Karol Bagh, Rohtak Road"},
                    new String[]{"PVR: 3CS", "Lajpat Nagar III"},
                    new String[]{"Cinepolis: Savitri Complex", "Greater Kailash 2"},
                    new String[]{"Batra Reels Cinemas", "New Friends Colony"},
                    new String[]{"INOX: Pacific Mall", "Jasola, Mathura Road"},
                    new String[]{"PVR: Sangam", "R.K. Puram"},
                    new String[]{"PVR: ECX Chanakyapuri", "Yashwant Place"},

                    // WEST DELHI
                    new String[]{"PVR: Pacific Mall", "Subhash Nagar, Tagore Garden"},
                    new String[]{"INOX: Janak Place", "Janakpuri District Centre"},
                    new String[]{"Cinepolis: Janak Cinema", "Janakpuri, Pankha Road"},
                    new String[]{"PVR: Vikaspuri", "Community Centre, Virjanand Marg"},
                    new String[]{"INOX: Patel Nagar", "Shadipur Metro Station"},
                    new String[]{"INOX: Vishal Mall", "Rajouri Garden"},
                    new String[]{"Wave: Raja Garden", "TDI Paragon Mall, Shivaji Place"},
                    new String[]{"INOX: RCube, Monad Mall", "Raja Garden"},
                    new String[]{"Miraj Cinemas: Ivory Tower", "Subhash Nagar"},

                    // NORTH DELHI
                    new String[]{"G3S Cinema", "Rohini Sector 11"},
                    new String[]{"Cinepolis: Unity One Mall", "Rohini Sector 10"},
                    new String[]{"M2K: Rohini", "Mangalam Place, Sector 3"},
                    new String[]{"PVR: Prashant Vihar", "Fun City Mall, Rohini Sec 14"},
                    new String[]{"M2K: Pitampura", "Road No. 44, Community Centre"},
                    new String[]{"PVR: Cinemagic", "Unity One Elegante, Pitampura"},
                    new String[]{"Cinepolis: Pacific NSP2", "Pitampura, NSP Metro"},
                    new String[]{"PVR: Shalimar Bagh", "DLF City Centre Mall"},
                    new String[]{"Amba Cinema", "Shakti Nagar, Ghanta Ghar"},
                    new String[]{"PVR: Anupam", "Saket Community Centre"}, 
                    new String[]{"Miraj Cinemas: Aakash", "Azadpur"},

                    // EAST DELHI
                    new String[]{"Cinepolis: V3S Mall", "Laxmi Nagar"},
                    new String[]{"Cinepolis: Cross River Mall", "Shahdara, Karkardooma"},
                    new String[]{"Miraj Cinemas: Vikas Cinemall", "Shahdara"},
                    new String[]{"Gagan Theatre", "Nand Nagri"},

                    // DWARKA
                    new String[]{"PVR: Vegas", "Dwarka Sector 14"},
                    new String[]{"PVR: Pacific", "Dwarka Sector 21"},

                    // NOIDA & GREATER NOIDA
                    new String[]{"PVR: Superplex, Mall Of India", "Noida Sector 18"},
                    new String[]{"PVR Director's Cut", "DLF Mall of India, Noida"},
                    new String[]{"PVR: Superplex Logix", "Noida City Centre, Sector 32"},
                    new String[]{"Wave: Noida", "Sector 18"},
                    new String[]{"Miraj Cinemas: TGIP", "The Great India Place, Sector 18"},
                    new String[]{"Cinepolis: Modi Mall", "Sector 25A, Noida"},
                    new String[]{"MovieMax Laserplex", "Gulshan One 29, Sector 129"},
                    new String[]{"MovieMax Edition", "Rcube Monad Mall, Noida"},
                    new String[]{"Movietime Cinemas", "Dharam Palace, Sector 18"},
                    new String[]{"PVR: Gaur City", "Greater Noida West"},
                    new String[]{"US CINEMAS: Galaxy Blue Sapphire", "Greater Noida West"},
                    new String[]{"INOX: Omaxe Connaught Place", "Greater Noida, Beta-2"},
                    new String[]{"Cinepolis: Grand Venice Mall", "Greater Noida, Pari Chowk"},
                    new String[]{"Rajhans Cinemas", "Galaxy Diamond Plaza, Greater Noida"},
                    new String[]{"MSX Cinemas", "Greater Noida, Industrial Area"},
                    new String[]{"7D Masti: Grand Venice", "Greater Noida"},

                    // GURUGRAM (GURGAON)
                    new String[]{"PVR: Ambience Mall", "NH-8, Gurugram"},
                    new String[]{"PVR Director's Cut", "Ambience Mall, Gurugram"},
                    new String[]{"HDFC Millennia PVR: MGF", "MG Road, Gurugram"},
                    new String[]{"PVR: City Centre", "DLF Phase 2, MG Road"},
                    new String[]{"PVR: Mega Mall", "DLF Phase 1, Golf Course Road"},
                    new String[]{"INOX: World Mark", "Sector 65, Maidawas Road"},
                    new String[]{"Cinepolis: Airia Mall", "Sector 68, Sohna Road"},
                    new String[]{"INOX: AIPL Joy Street", "Sector 66, Badshahpur"},
                    new String[]{"PVR: Elan Miracle", "Sector 84, Dwarka Expressway"},
                    new String[]{"PVR: Elan Town Centre", "Sector 67, Sohna Road"},
                    new String[]{"PVR: Elan Mercado", "Sector 80, NH-8"},
                    new String[]{"Devgn CineX", "Elan Epic Mall, Sector 70"},
                    new String[]{"Cinepolis: The Esplanade", "Sector 37C"},
                    new String[]{"Skylit Cinemas", "Sahara Mall, MG Road"},
                    new String[]{"Wave: Urbana Premium", "Sector 67"},
                    new String[]{"MovieMax: Ansal Plaza", "Palam Vihar"},
                    new String[]{"INOX: Sapphire 90", "Sector 90"},
                    new String[]{"INOX: Ardee Mall", "Sector 52"},
                    new String[]{"INOX: Gurgaon Sapphire 83", "Sector 83"},
                    new String[]{"INOX: IRIS Broadway", "Sector 85"},
                    new String[]{"Miraj Maximum", "Metropolis Mall, MG Road"},
                    new String[]{"Miraj Cinemas: KLJ Square", "Sector 83"},
                    new String[]{"Connplex Cinemas", "Floreal Towers, Sector 83"},
                    new String[]{"Cineport Cinemas", "SVH Metro Street, Sector 83"},
                    new String[]{"Mad Cinemas", "Newtown Square, Sector 95A"},
                    new String[]{"RR Cinema: Omaxe", "Sector 49, Sohna Road"},
                    new String[]{"Cinepolis: Grand View", "Sector 58"},
                    new String[]{"QLA Cinemas", "Dremz Mall, Old Railway Road"},
                    new String[]{"Movietime Cinemas", "Celebration Mall, Sohna Road"},
                    new String[]{"Legend Cinema Lounges", "Mall Fifty One, Sector 51"},

                    // GHAZIABAD & INDIRAPURAM
                    new String[]{"PVR: Mahagun", "Vaishali Sector 3"},
                    new String[]{"PVR: EDM", "Kaushambi, East Delhi Mall"},
                    new String[]{"Wave: Kaushambi", "The Wave Mall"},
                    new String[]{"PVR: VVIP", "Raj Nagar Extension"},
                    new String[]{"PVR: Opulent", "Grand Trunk Road"},
                    new String[]{"INOX: Shipra Mall", "Indirapuram"},
                    new String[]{"US CINEMAS: Aditya Mall", "Indirapuram"},
                    new String[]{"US CINEMAS: Eros Mall", "Indirapuram"},
                    new String[]{"RR Cinema: Jaipuria Mall", "Indirapuram"},
                    new String[]{"MovieMax: Pacific Mall", "Sahibabad"},
                    new String[]{"Wave Cinemas: Gaur Central", "RDC Raj Nagar"},
                    new String[]{"B18 Cinemas", "City Center, Mohan Nagar"},
                    new String[]{"Devgn CineX", "Ansa City Centre, Rajnagar Ext"},
                    new String[]{"Galaxie Multiplex", "Sahibabad"},
                    new String[]{"7D Masti: EDM Mall", "Kaushambi"},
                    new String[]{"7D Masti: Shipra Mall", "Indirapuram"},
                    new String[]{"ROONGTA CINEMAS", "Shopprix Mall, Vaishali"},
                    new String[]{"Silvercity Multiplex", "G.T. Road"},
                    new String[]{"US Cinemas: Movie World", "Sihani Chungi"},
                    new String[]{"Movie Magic Cinema", "Loni"},
                    new String[]{"Kavita Multiplex", "Loni"},
                    new String[]{"Meenakshi Multiplex", "Loni"},
                    new String[]{"Miraj Cinemas: M4U", "Sahibabad"},
                    new String[]{"Madhuban Cinema", "Dasna"},

                    // FARIDABAD
                    new String[]{"PVR: Pebble Downtown", "Sector 12"},
                    new String[]{"PVR: Pacific Mall", "NIT Bus Stand"},
                    new String[]{"INOX: Crown Interiorz", "Mathura Road"},
                    new String[]{"INOX: EF3 Mall", "Sector 20A"},
                    new String[]{"PVR: Piyush Mahendra", "Metro Road"},
                    new String[]{"Miraj Cinemas: Eldeco", "Sector 12"},
                    new String[]{"MSX Silvercity", "Haldiram Citymall, Sector 12"},
                    new String[]{"Fun Cinemas: Parsvnath", "Manhattan Mall, Sector 20A"},
                    new String[]{"AKR Cinemas", "SLF Mall, Sector 32"},
                    new String[]{"Pristine Mall", "Sector 31"},

                    // PILKHUWA & KUNDLI
                    new String[]{"Satyam VS Cinema", "Pilkhuwa"},
                    new String[]{"Vibhor Chitralok", "Pilkhuwa"},
                    new String[]{"AKR Cinemas", "TDI Mall, Kundli"},
                    new String[]{"eBox Cinema", "Parker Mall, Kundli"},
                    new String[]{"eBox Cinema", "Sonic World Mall, Surajpur"}
                );

                for (String[] data : delhiCinemas) {
                    Cinema c = new Cinema();
                    c.setName(data[0]);
                    c.setAddress(data[1]);
                    c.setType("Multiplex");
                    c.setCity(delhi);
                    cinemaRepo.save(c);
                }
                System.out.println("✅ Loaded " + delhiCinemas.size() + " Cinemas for Delhi-NCR.");
            }
         // 4. Populate Bengaluru Cinemas (From Screenshots)
            City bengaluru = cityRepo.findByName("Bengaluru");
            if (bengaluru != null) {
                List<String[]> blrCinemas = Arrays.asList(
                    // MALLS & MULTIPLEXES - CENTRAL
                    new String[]{"PVR: Orion Mall", "Dr Rajkumar Road, Malleshwaram"},
                    new String[]{"INOX: Mantri Square", "Malleshwaram"},
                    new String[]{"PVR: Renaissance", "Malleshwaram"},
                    new String[]{"INOX: Lido", "Off MG Road, Ulsoor"},
                    new String[]{"PVR: Director's Cut, Forum Rex Walk", "Brigade Road"},
                    new String[]{"Swagath ShankarNag (ONYX) LED", "MG Road"},
                    new String[]{"INOX: Garuda Mall", "Magrath Road"},
                    new String[]{"Gopalan Cinemas: Arcade Mall", "Mysore Road"},
                    new String[]{"Gopalan Mall: Sirsi Circle", "Mysore Road"},
                    
                    // KORAMANGALA & HSR & BTM
                    new String[]{"PVR: Nexus (Formerly Forum)", "Koramangala"},
                    new String[]{"Cinephile PNR Felicity Mall", "HSR Layout"},
                    new String[]{"Sandhya RGB Laser Atmos", "BTM Layout"},
                    new String[]{"Balaji Theatre", "Tavarekere (Near BTM)"},
                    new String[]{"Lakshmi Cinema 4K Dolby Atmos", "Tavarekere"},
                    new String[]{"Srinivasa Cinema 4K Dolby Atmos", "SG Palya"},

                    // SOUTH BENGALURU (JP NAGAR, BANNERGHATTA, ETC)
                    new String[]{"PVR: Vega City", "Bannerghatta Road"},
                    new String[]{"Cinepolis: Royal Meenakshi Mall", "Bannerghatta Road"},
                    new String[]{"Cinepolis: SJR (Central Mall)", "Arekere, Bannerghatta"},
                    new String[]{"Gopalan Cinemas", "Bannerghatta Road"},
                    new String[]{"INOX: Central", "JP Nagar, Mantri Junction"},
                    new String[]{"Siddeshwara 4K Dolby Atmos 3D", "JP Nagar"},
                    new String[]{"Sri Renuka Prasanna Theatre", "JP Nagar"},
                    new String[]{"PVR: Superplex Forum Mall", "Kanakapura Road"},
                    new String[]{"Manasa RGB Laser ATMOS", "Konanakunte"},
                    new String[]{"Sri Lakshmi A/C 4k Dolby 7.1", "Gottigere"},
                    new String[]{"Kamakya 4K Dolby Atmos", "Banashankari"},
                    new String[]{"Mahadeshwara Cinema 4K", "Banashankari"},
                    new String[]{"Sri Venkateshwara Digital 4K", "Girinagar"},

                    // EAST BENGALURU (WHITEFIELD, MARATHAHALLI)
                    new String[]{"PVR: Phoenix Marketcity", "Whitefield Road"},
                    new String[]{"PVR: VR Bengaluru", "Whitefield Road"},
                    new String[]{"Cinepolis: Nexus Shantiniketan", "Whitefield"},
                    new String[]{"INOX: Nexus", "Whitefield"},
                    new String[]{"PVR: Aura Park Square", "Whitefield"},
                    new String[]{"INOX: Brookefield Mall", "Kundalahalli"},
                    new String[]{"INOX: SBR Horizon", "Seegehalli, Whitefield"},
                    new String[]{"PVR: Soul Spirit Central Mall", "Bellandur"},
                    new String[]{"Sri Vinayaka Cinemas 4K Dolby", "Varthur"},
                    new String[]{"Kino Cinemas", "Seegehalli, Kadugodi"},

                    // NORTH BENGALURU (YELAHANKA, HEBBAL)
                    new String[]{"INOX: Megaplex Mall of Asia", "Byatarayanapura, Yelahanka"},
                    new String[]{"INOX: Galleria Mall", "Yelahanka"},
                    new String[]{"PVR: Bhartiya Mall of Bengaluru", "Thanisandra Main Road"},
                    new String[]{"PVR: MSR Elements Mall", "Tanisandra Main Road"},
                    new String[]{"PVR: Vaishnavi Sapphire Mall", "Yeshwanthpur"},
                    new String[]{"Ganesh Digital 2K Cinema", "Yelahanka New Town"},
                    new String[]{"Prakash Theatre", "Yelahanka"},
                    new String[]{"Rockline Cinemas", "Jalahalli Cross"},
                    new String[]{"HMT Cinema", "Jalahalli"},
                    new String[]{"Goverdhan Theatre", "Yeshwantpur"},
                    new String[]{"Murali Cinemas (Gokula)", "Mathikere"},
                    new String[]{"Sri Radhakrishna Theatre", "RT Nagar"},
                    new String[]{"Venkateshwara Cinemas 4K", "Gollarahati"},

                    // OLD MADRAS ROAD & KR PURAM
                    new String[]{"Gopalan Grand Mall", "Old Madras Road"},
                    new String[]{"PVR: Orion Uptown", "Old Madras Road"},
                    new String[]{"Gopalan Miniplex: Signature Mall", "Old Madras Road"},
                    new String[]{"Venkateshwara A/c 4K Dolby Atmos", "K.R.Puram"},
                    new String[]{"Movietime Cinemas: YGR Signature", "RR Nagar"}, 
                    new String[]{"Pushpanjali B N Pura", "Dooravani Nagar"},

                    // ELECTRONIC CITY & HOSUR ROAD
                    new String[]{"INOX: M5 Ecity", "Veerasandra Industrial Area"},
                    new String[]{"Venkateshwara Theatre", "Konappana Agrahara (E.City)"},
                    new String[]{"Sri Krishna Lazer Projection 4K", "Bomanahalli"},
                    new String[]{"Brundha RGB Laser 4K", "Hongasandra"},
                    new String[]{"Galaxy Paradise", "Begur Road"},

                    // FAMOUS SINGLE SCREENS (GANDHINAGAR & MAJESTIC)
                    new String[]{"Santosh 4K Dolby Theatre", "Gandhinagar"},
                    new String[]{"Narthaki 4K Dolby 7.1", "Gandhinagar"},
                    new String[]{"Triveni Theatre A/C 3D 4K", "Gandhinagar"},
                    new String[]{"Anupama Theatre A/C 4K", "Gandhinagar"},
                    new String[]{"Bhumika Digital 2K Cinema", "Gandhinagar"},
                    new String[]{"Abhinay Theatre 4K A/C", "Gandhinagar"},
                    new String[]{"Sapna 2K Dolby 5.1", "Gandhinagar"},
                    new String[]{"Movieland Theatre", "Gandhinagar"},
                    new String[]{"Savitha Theatre", "Malleshwaram"},
                    new String[]{"Navrang Theatre", "Rajaji Nagar"},
                    new String[]{"Veeresh Cinemas", "Magadi Road"},
                    new String[]{"Prasanna Digital 4K Cinema", "Magadi Road"},
                    new String[]{"Anjan Digital 4K A/C Cinema", "Magadi Road"},
                    new String[]{"PVR: GT World Mall", "Magadi Road"},

                    // OUTSKIRTS (RR NAGAR, KENGERI, ETC)
                    new String[]{"Cinepolis: Binnypet Mall", "Binneypet"},
                    new String[]{"PVR: Global Mall", "Mysore Road"},
                    new String[]{"Robin Theater 4K Dolby Atmos", "Kengeri"},
                    new String[]{"Venkateshwara Digital 4K", "Kengeri"},
                    new String[]{"Miraj Cinemas: TGS Lotus Elite", "Sunkadakatte"},
                    new String[]{"Mohan Cinema Barco 4K", "Sunkadakatte"},
                    new String[]{"Victory Cinema Barco-4K", "Kamakshipalya"},
                    new String[]{"Akash Cinemas", "Laggere"},
                    new String[]{"VR Cinemas 4K A/C", "Mallathalli"},
                    new String[]{"Deepak Talkies", "Bidadi"}
                );

                for (String[] data : blrCinemas) {
                    Cinema c = new Cinema();
                    c.setName(data[0]);
                    c.setAddress(data[1]);
                    c.setType("Multiplex");
                    c.setCity(bengaluru);
                    cinemaRepo.save(c);
                }
                System.out.println("✅ Loaded " + blrCinemas.size() + " Cinemas for Bengaluru.");
            }
         // 5. Populate Hyderabad Cinemas (From Screenshots)
            City hyderabad = cityRepo.findByName("Hyderabad");
            if (hyderabad != null) {
                List<String[]> hydCinemas = Arrays.asList(
                    // HITEC CITY, GACHIBOWLI & MADHAPUR
                    new String[]{"AMB Cinemas", "Gachibowli"},
                    new String[]{"PVR ICON: Hitech", "Madhapur"},
                    new String[]{"PVR: Inorbit Cyberabad", "Hitech City, Madhapur"},
                    new String[]{"Prasads Multiplex", "Khairtabad, NTR Gardens"},
                    new String[]{"PVR: Atrium", "Gachibowli"},
                    new String[]{"PVR: Preston Prime", "Gachibowli"},
                    new String[]{"Platinum Movietime: SLN Terminus", "Gachibowli"},
                    new String[]{"BR Hitech 70mm", "Madhapur"},
                    new String[]{"Aparna Cinemas", "Nallagandla"},

                    // KUKATPALLY & MIYAPUR
                    new String[]{"PVR: Nexus Mall", "Kukatpally"},
                    new String[]{"Asian Lakshmikala Cinepride", "Moosapet"},
                    new String[]{"Sree Ramulu 70mm 4k Laser", "Moosapet"},
                    new String[]{"Mallikarjuna 70MM A/C DTS", "Kukatpally"},
                    new String[]{"Bhramaramba 70MM A/C 4K Dolby", "Kukatpally"},
                    new String[]{"INOX: Ashoka One Mall", "Kukatpally Y Junction"},
                    new String[]{"Arjun 70MM", "Kukatpally"},
                    new String[]{"Miraj Cinemas: CineTown", "Miyapur"},
                    new String[]{"Sai Ranga 70MM 4k Dolby 7.1", "Miyapur"},
                    new String[]{"Asian Cineplanet Multiplex", "Kompally"},
                    new String[]{"GPR Multiplex", "Nizampet"},

                    // SECUNDERABAD & NORTH
                    new String[]{"MovieMax: AMR Planet", "ECIL, Secunderabad"},
                    new String[]{"Tivoli Cinemas", "Secunderabad"},
                    new String[]{"Prashant Cinema", "Secunderabad"},
                    new String[]{"Asian Radhika Multiplex", "ECIL"},
                    new String[]{"Cinepolis: TNR North City", "Suchitra"},
                    new String[]{"Sri Sai Ram 70mm A/C", "Malkajgiri"},
                    new String[]{"Cinepolis: CCPL Mall", "Malkajgiri"},
                    new String[]{"Miraj Cinemas: Raghavendra", "Malkajgiri"},
                    new String[]{"Asian Sha & Shahensha", "Chintal"},
                    new String[]{"Bhujanga 70MM", "Jeedimetla"},
                    new String[]{"Asian Mukund Cinema", "Medchal"},
                    new String[]{"Lakshmi Kala Mandir", "Alwal"},
                    new String[]{"Talluri Theatres", "Kushaiguda"},
                    new String[]{"VLS Sridevi 2K A/C Dts", "Chilakalguda"},

                    // CENTRAL & BANJARA HILLS
                    new String[]{"PVR: Next Galleria Mall", "Panjagutta"},
                    new String[]{"PVR: Central Mall", "Panjagutta"},
                    new String[]{"INOX: GVK One", "Banjara Hills"},
                    new String[]{"PVR: RK Cineplex", "Banjara Hills"},
                    new String[]{"Connplex Cinemas: MPM Mall", "Banjara Hills"},
                    new String[]{"PVR: Irrum Manzil", "Khairatabad"},
                    new String[]{"Asian Mukta A2 Sensation", "Khairatabad"},
                    new String[]{"AAA Cinemas", "Ameerpet"},
                    new String[]{"INOX: Sattva Necklace Mall", "Kavadiguda"},
                    new String[]{"Sree Sai Raja Theatre", "Musheerabad"},

                    // RTC X ROADS (THE HUB)
                    new String[]{"Sandhya 70MM 4K Dolby Atmos", "RTC X Roads"},
                    new String[]{"Sandhya 35mm 2k Dolby Atmos", "RTC X Roads"},
                    new String[]{"Sudarshan 35MM 4k Laser", "RTC X Roads"},
                    new String[]{"Devi 70MM 4K Laser", "RTC X Roads"},
                    new String[]{"Saptagiri 70MM 4K", "RTC X Roads"},

                    // EAST (UPPAL, DILSUKHNAGAR, LB NAGAR)
                    new String[]{"Cinepolis: DSL Virtue Mall", "Uppal"},
                    new String[]{"Asian Rajya Lakshmi", "Uppal"},
                    new String[]{"Sri Krishna 70MM", "Uppal"},
                    new String[]{"PVR: Musarambagh", "Malakpet"},
                    new String[]{"Miraj Cinemas: Shalini Shivani", "Kothapet"},
                    new String[]{"BVK Multiplex Vijayalakshmi", "LB Nagar"},
                    new String[]{"Cine Town Indra Nagendra", "Karmanghat"},
                    new String[]{"Sree Ramana 70MM 4K", "Amberpet"},
                    new String[]{"ART CINEMAS", "Vanasthalipuram"},
                    new String[]{"Sushma 2K Dolby Digital", "Vanasthalipuram"},
                    new String[]{"Santosh Theatre", "Ibrahimpatnam"},

                    // SOUTH & OLD CITY
                    new String[]{"Cinepolis: Lulu Mall", "Hyderabad"},
                    new String[]{"Cinepolis: Mantra Mall", "Attapur"},
                    new String[]{"Asian M Cube Mall", "Attapur"},
                    new String[]{"Cinepolis: Sudha Cinemas", "Bahadurpura"},
                    new String[]{"Metro Cinema", "Bahadurpura"},
                    new String[]{"Laxmi 70MM A/C LASER", "Shamshabad"},
                    new String[]{"Asian Super Cinema", "Balapur"},
                    new String[]{"Yakut Mahal Theater", "Yakutpura"},
                    new String[]{"Ramakrishna Glitterati", "Abids"},
                    new String[]{"Rama Krishna 70mm", "Abids"},
                    new String[]{"Aradhana Theatre", "Hyderabad"},
                    new String[]{"Alankar (Pratap Theatre)", "Langer House"},

                    // OTHER AREAS
                    new String[]{"INOX: GSM Mall", "Miyapur"},
                    new String[]{"JP Cinemas", "Chandanagar"},
                    new String[]{"Miraj Cinemas: Geeta", "Chandanagar"},
                    new String[]{"Miraj Cinemas: Anand Mall", "Narsingi"},
                    new String[]{"INOX: Prism Mall", "Gachibowli"}, // Actually near Gachibowli
                    new String[]{"Asian Cinemart", "RC Puram"},
                    new String[]{"Asian Jyothi", "RC Puram"},
                    new String[]{"Gokul 70MM 4K DTS", "Erragadda"},
                    new String[]{"Movietime Cinemas: SKY Mall", "Erragadda"},
                    new String[]{"Asian Tarakarama Cineplex", "Kachiguda"},
                    new String[]{"INOX: Maheshwari Parmeshwari", "Kachiguda"},
                    new String[]{"Indra Venkataramana Padmavati", "Kachiguda"},
                    new String[]{"PVR: Lakeshore Mall", "Y Junction"},
                    new String[]{"ROONGTA CINEMAS: NOVUM", "Nampally"},
                    new String[]{"UK Cineplex", "Nacharam"},
                    new String[]{"Vyjayanthi Cinema A/C 2K", "Nacharam"},
                    new String[]{"INOX: SMR Vinay Metro Mall", "Miyapur"}, // Check location, extracted from image
                    new String[]{"Venkata Sai Theatre AC", "Keesara"},
                    new String[]{"Sri Krishna Theatre", "Aliabad"}
                );

                for (String[] data : hydCinemas) {
                    Cinema c = new Cinema();
                    c.setName(data[0]);
                    c.setAddress(data[1]);
                    c.setType("Multiplex");
                    c.setCity(hyderabad);
                    cinemaRepo.save(c);
                }
                System.out.println("✅ Loaded " + hydCinemas.size() + " Cinemas for Hyderabad.");
            }
         // 6. Populate Chandigarh Cinemas (From Screenshot)
            City chandigarh = cityRepo.findByName("Chandigarh");
            if (chandigarh != null) {
                List<String[]> chdCinemas = Arrays.asList(
                    // CHANDIGARH CITY
                    new String[]{"PVR: Elante, Chandigarh", "Plot No 178-178 A, 3rd Floor, Elante Mall, Industrial Area Phase I"},
                    new String[]{"Piccadily Square: Chandigarh", "Sub. City Center, Sector 34-A, Chandigarh"},
                    new String[]{"PVR: Centra, Chandigarh", "177/D, Centra Mall, Industrial Phase-1, Near Tribune Chowk"},
                    new String[]{"Cinepolis: Jagat Mall, Chandigarh", "Plot No 32, TDI Mall, Sector 17 A, Chandigarh"},
                    new String[]{"PVR: City Centre IT Park, Chandigarh", "Plot No-22/23, 2nd Floor, DLF City Center Mall, I.T Park, Manimajra"},
                    new String[]{"Fun Cinemas: Republic Mall, Chandigarh", "Dhillon Complex, Mani Majra, Chandigarh"},

                    // MOHALI
                    new String[]{"PVR: CP67 Mall, Mohali", "2nd Floor CP67 Mall, Sector 67, Sahibzada Ajit Singh Nagar"},
                    new String[]{"PVR: VR Punjab Mall, Mohali", "NH21, North Country Mall, 2nd Floor, Sector 118, Sahibzada Ajit Singh Nagar"},
                    new String[]{"PVR: MOHALI WALK", "rd Floor, Plot No. 1-2, Sector- 62, SAS NAGAR"},
                    new String[]{"Cinepolis: Bestech Square, Mohali", "Bestech Square Mall, Sector 66, Sahibzada Ajit Singh Nagar"},

                    // ZIRAKPUR
                    new String[]{"PVR Cosmo: Zirakpur", "Cosmo Mall, Ambala Chandigarh Express Highway, Zirakpur"},
                    new String[]{"INOX: Dhillon Plaza (Zirakpur)", "2nd Floor, Dhillon Square Mall, Chhatbir Road"},
                    new String[]{"Legend Cinemas: Paras Downtown Square, Zirakpur", "Paras Down Town Square Mall, Ambala Chandigarh Express Way"},

                    // PANCHKULA
                    new String[]{"Rajhans Cinemas: Panchkula", "Hi5 Mall, Sector 5, Opposite KC Theater, Panchkula"},
                    new String[]{"INOX: NH22 Mall, Amravati Enclave, Panchkula", "Amravati, Enclave, Chandigarh, Chandigarh"}
                );

                for (String[] data : chdCinemas) {
                    Cinema c = new Cinema();
                    c.setName(data[0]);
                    c.setAddress(data[1]);
                    c.setType("Multiplex");
                    c.setCity(chandigarh);
                    cinemaRepo.save(c);
                }
                System.out.println("✅ Loaded " + chdCinemas.size() + " Cinemas for Chandigarh.");
            }
         // 7. Populate Ahmedabad Cinemas (From Screenshots)
            City ahmedabad = cityRepo.findByName("Ahmedabad");
            if (ahmedabad != null) {
                List<String[]> ahmCinemas = Arrays.asList(
                    // WEST AHMEDABAD (Thaltej, Bodakdev, Satellite, Vastrapur)
                    new String[]{"PVR: Palladium Mall, Ahmedabad", "4th Floor, Palladium Mall, SG Highway, Thaltej"},
                    new String[]{"PVR: Acropolis, Ahmedabad", "Acropolis Mall, Thaltej Cross Road"},
                    new String[]{"Cinepolis: Nexus Ahmedabad One", "Alpha One Mall, Vastrapur Lake"},
                    new String[]{"Mukta A2 Cinemas: The Retail Park (TRP) Bopal", "The Retail Park, Bopal"},
                    new String[]{"AB Miniplex: Shivranjini Cross Road", "Sheetal Varsha, Satellite Road"},
                    new String[]{"Connplex Cinemas: Prahladnagar", "Campus Corner 2, Prahlad Nagar"},
                    new String[]{"Connplex Cinemas: Shilaj", "Shilp The Address, Shilaj Road"},
                    new String[]{"Connplex Cinemas (Luxuriance): SBR", "Shilp Epitome, Bodakdev"},
                    new String[]{"Mukta A2 Cinemas: Ratnanjali Square Satellite", "Ratnanjali Infra LLP, Satellite"},
                    new String[]{"Sanelite Cinemas (Banana Smartplex): SBR", "Times Square Grand, Thaltej"},
                    new String[]{"City Pulse Miniplex: Iscon Circle", "Baleshwar Square, SG Highway"},
                    new String[]{"1,Newfangled Miniplex (Twin Seat): Mondeal Park", "Mondeal Retail Park, SG Road"},
                    new String[]{"Magic Cinema: YMCA Club", "YMCA International Center, SG Highway"},

                    // NORTH AHMEDABAD (Motera, Chandkheda, Sabarmati)
                    new String[]{"Rajhans Cinemas: The CBD Mall", "Zundal Circle, SP Ring Road"},
                    new String[]{"PVR: Arved Transcube", "Ranip, Ahmedabad"},
                    new String[]{"Devgn CineX: Chandkheda", "Aamrakunj Arne, Nigam Nagar"},
                    new String[]{"PVR: Motera, Ahmedabad", "4D Square Mall, Visat-Gandhinagar Highway"},
                    new String[]{"Sanelite Cinemas: Agora Mall", "Agora Mall, Motera"},
                    new String[]{"Mukta A2 Cinemas: Chandkheda", "KB Royal Pheonix, Chandkheda"},
                    new String[]{"LHD Cinemas: Chandkheda", "Sai Sapphire Square, Chandkheda"},
                    new String[]{"City Gold: Motera", "Indira Nagar, Motera Stadium Road"},
                    new String[]{"2,Newfangled Miniplex (Twin Seat): Motera", "North Plaza, Motera"},

                    // EAST AHMEDABAD (Maninagar, Vastral, Nikol, Naroda)
                    new String[]{"Rajhans Cinemas: Vastral", "Ved Arcade Mall, SP Ring Road"},
                    new String[]{"Mango Plus Cinemas: Nikol", "Fortune Sky, Fortune Circle"},
                    new String[]{"Cinepolis: Kankubag, Vastral", "Nirant Cross Road, Metro Station"},
                    new String[]{"Miraj Cinemas: City Pulse", "Kankaria Road, Raipur"},
                    new String[]{"Rajhans Cinemas: Nikol", "Pavillion Mall, New Nikol"},
                    new String[]{"PVR: Satyamev Emporio Odhav", "Satyamev Emporio Mall, Odhav"},
                    new String[]{"Apple Multiplex: Maninagar", "Gita Mandir Road, Maninagar"},
                    new String[]{"Miraj Cinemas: Cinepride", "Krishna Nagar, Naroda-Narol"},
                    new String[]{"Devi Multiplex: Naroda", "Naroda, Near Axis Bank ATM"},
                    new String[]{"Apple Cinema: Bapunagar", "White House, India Colony"},
                    new String[]{"Havelock Cineflix Cinemas: Maninagar", "Eka Club by TransStadia, Kankaria Lake"},
                    new String[]{"Cineprime Cinema: Nikol", "Hiltown Square, MG Road"},
                    new String[]{"Miraj Cinemas: Vitthal Plaza", "New Naroda Dahegam Road"},
                    new String[]{"Orange Cinemas: Bapunagar", "Pushkar Business Park, Bapunagar"},
                    new String[]{"Mira Cinema: Ahmedabad", "Bhairavnath Road, Maninagar"},
                    new String[]{"Revolution Multiplex, CTM", "CTM Cross Road"},

                    // GANDHINAGAR & OUTSKIRTS
                    new String[]{"Devgn CineX: Swagat Mall", "Sargasan, Gandhinagar"},
                    new String[]{"Connplex Cinemas (Signature): Gandhinagar", "Shreeji Signature, Sargasan"},
                    new String[]{"Classic Cinema, Kudasan", "Pramukh Anand Mall, Gandhinagar"},
                    new String[]{"White Screen Cinema (Five 11)", "Indroda Park, Gandhinagar"},
                    new String[]{"Connplex Cinemas (Luxuriance): Vaishnodevi", "Master Avenue, Gandhinagar"},
                    new String[]{"The Cinestar Miniplex: Bhat Circle", "Xperia, SP Ring Road, Gandhinagar"},

                    // CENTRAL & OTHER AREAS
                    new String[]{"INOX: Himalaya Mall", "Drive In Road, Memnagar"},
                    new String[]{"City Gold: Ashram Road", "Muslim Society, Navrangpura"},
                    new String[]{"Connplex Cinemas: Parimal", "Parimal Garden, Ambavadi"},
                    new String[]{"Apple Multiplex: Gota", "Vandematram, Gota Road"},
                    new String[]{"Connplex Cinemas (Signature): Gota", "The Link, Gota"},
                    new String[]{"City Gold Satellite: Ahmedabad", "Shyamal Cross Road"},
                    new String[]{"Sanelite Cinemas: Science City", "The Obelisk, Science City Road"},
                    new String[]{"Mukta A2 Shiv Cinema", "Ashram Road"},
                    new String[]{"Mukta A2 Cinemas: Rajyash", "South Vasna"},
                    new String[]{"Connplex Cinemas: South Bopal", "Gala Gymkhana Road, South Bopal"},
                    new String[]{"City Gold: Bopal", "Amrapali Shopping Complex, Bopal"},
                    new String[]{"Miraj Cinemas: Vardhman Square", "Sanand, Ahmedabad"},
                    new String[]{"SK Cinemas: Hathijan", "Lalgebi Circle, Hathijan"},
                    new String[]{"Cineprime Cinema: Luxuriance", "Vishala Empire, Hanspura"},
                    new String[]{"City Gold: Jivraj Park", "Avadh Hotel, Jivraj Park"},
                    new String[]{"Sanelite Cinemas: South Bopal", "Gamara Capital, South Bopal"},
                    new String[]{"P Square Movieplex, Gota", "Vandemataram Icon, Gota"},
                    new String[]{"Rupam Multiplex: Ahmedabad", "Relief Road, Gheekanta"},
                    new String[]{"Rupam Arth Cineplex: Sanand", "Station Road, Sanand"},
                    new String[]{"Savvy Swaraaj Miniplex: Gota", "Godrej Garden City Road"},
                    new String[]{"City Pulse: Orient Miniplex", "Gujarat College, Ellisbridge"}
                );

                for (String[] data : ahmCinemas) {
                    Cinema c = new Cinema();
                    c.setName(data[0]);
                    c.setAddress(data[1]);
                    c.setType("Multiplex");
                    c.setCity(ahmedabad);
                    cinemaRepo.save(c);
                }
                System.out.println("✅ Loaded " + ahmCinemas.size() + " Cinemas for Ahmedabad.");
            }
         // 8. Populate Pune Cinemas (From Screenshots)
            City pune = cityRepo.findByName("Pune");
            if (pune != null) {
                List<String[]> puneCinemas = Arrays.asList(
                    // WEST PUNE (Wakad, Hinjawadi, Aundh, Baner)
                    new String[]{"INOX: Megaplex Phoenix Mall of the Millennium", "3rd Floor, Phoenix Mall of the Millennium, Wakad"},
                    new String[]{"E-SQUARE: Xion Mall, Hinjawadi", "1st Floor, XION Mall, Hinjawadi Road, Wakad"},
                    new String[]{"PVR: Grand Highstreet Mall, Hinjawadi", "Grand Highstreet Mall, 2nd Floor, Hinjawadi"},
                    new String[]{"Cinepolis: Nexus WESTEND Mall, Aundh", "Wireless Colony, Aundh, Pune"},
                    new String[]{"PVR: Icon, The Pavillion Pune", "Pavillion Mall, 3rd Floor, Senapati Bapat Road"},

                    // EAST PUNE (Viman Nagar, Kharadi, Hadapsar, Magarpatta)
                    new String[]{"PVR: Phoenix Market City, Pune", "2nd Floor, Phoenix Market City, Nagar Road, Viman Nagar"},
                    new String[]{"Cinepolis: Seasons Mall, Pune", "3rd Floor, Seasons Mall, Magarpatta City, Hadapsar"},
                    new String[]{"City Pride, Nyati Plaza: Kharadi", "Nyati Plaza, Thite Nagar, Kharadi"},
                    new String[]{"MovieMax: Amanora Town Centre", "Amanora Park Town, Hadapsar"},
                    new String[]{"Bollywood Multiplex: Kharadi", "Old Mundhwa Road, Sector Number 3, Kharadi"},
                    new String[]{"MovieMax Gold: Mariplex Mall, Kalyani Nagar", "Marigold Complex, Kalyani Nagar"},
                    new String[]{"Vaibhav Chitramandir: Hadapsar", "Pune Solapur Road, Near Canara Bank, Hadapsar"},
                    new String[]{"MovieMax Edition (Luxe): Amanora", "Amanora Town Centre, Amanora Park Town"},
                    new String[]{"Cinepolis: VIP Seasons Mall, Pune", "Magarpatta Police Station Road, Hadapsar"},

                    // CENTRAL PUNE (Shivajinagar, Camp, Deccan, Kothrud)
                    new String[]{"City Pride: Kothrud", "Survey No.20/1,2, Kothrud, Near Tara Residency"},
                    new String[]{"PVR: Kumar Pacific, Pune", "4th Floor, Kumar Pacific Mall, Shankar Seth Road"},
                    new String[]{"E-SQUARE: University Road", "University Road, Premnagar, Ashok Nagar"},
                    new String[]{"Rahul 70 MM: Shivajinagar", "Ganeshkhind Road, Shivaji Nagar"},
                    new String[]{"City Pride: Mangala Cinema", "Shivaji Road, Shivaji Nagar"},
                    new String[]{"INOX: Bund Garden Road", "Plot No.D, Bund Garden Road, Agarkar Nagar"},
                    new String[]{"PVR: Directors Cut, KOPA, Pune", "KOPA Mall, Ghorpadi, Mundhwa Road"},
                    new String[]{"City Pride: R Deccan", "R Deccan Mall, Jangli Maharaj Road, Deccan"},
                    new String[]{"Victory Theatre: Camp", "Gen Thimayya Road, Camp"},
                    new String[]{"CinePRO: Vasant Cinema", "Near Nana Wada, Shivaji Maharaj Road, Budhwar Peth"},

                    // SOUTH PUNE (Sinhagad Rd, Satara Rd, NIBM)
                    new String[]{"Abhiruchi City Pride: Sinhagad Road", "Near Flyover, Sinhagad Road, Vadgaon Budruk"},
                    new String[]{"City Pride: Satara Road", "Market Yard, Pune Satara Road"},
                    new String[]{"INOX: Royal Heritage Mall, NIBM", "Royale Heritage Mall, Mohammed Wadi, NIBM Ext"},
                    new String[]{"Rajhans Cinemas: 93 Avenue Mall", "Fatima Nagar Junction, Wanoworie"},
                    new String[]{"Fun Time Multiplex: Sinhagad Road", "Near Manik Baug Petrol Pump, Sinhagad Road"},

                    // PIMPRI-CHINCHWAD (PCMC)
                    new String[]{"INOX: Elpro City Square, Chinchwad", "2nd Floor, Elpro City Square Mall, Chinchwad"},
                    new String[]{"Vishal Cinemaas: Pimpri", "Old Pune-Mumbai Highway, Pimpri-Chinchwad"},
                    new String[]{"Miraj Cinemas: Spine City Mall", "Spine Road, Sant Nagar, Pune"},
                    new String[]{"City Pride Royal Cinemas: Rahatani", "Spot-18 Mall, 3rd Floor, Rahatani"},
                    new String[]{"INOX: Jai Ganesh, Akurdi", "Jai Ganesh Vision Mall, Akurdi Chowk"},
                    new String[]{"Funtime Deluxe: Pimpri", "Deluxe Fortune Mall, Shastri Nagar, Pimpri"},
                    new String[]{"Ashok Theatre: Pimpri", "Shastri Nagar, Pimpri Colony"},

                    // OUTSKIRTS & RURAL (Talegaon, Chakan, etc.)
                    new String[]{"Shri Shivaji Talkies: Talegaon", "Somwar Peth, Talegaon Dabhade"},
                    new String[]{"Laxmi Cineplex: Narayangaon", "Telephone Exchange Road, Narayangaon"},
                    new String[]{"Dharmatma Cinemark: Chakan", "City Plaza, Talegaon, Chakan Road"},
                    new String[]{"Vikas Cinema: Manchar", "Tal: Ambegaon, At Post Manchar"},
                    new String[]{"Funsquare Cinema: Ghotawade Phata", "Pirangut, Pune"},
                    new String[]{"Vilux Talkies: Khadki", "New Excelsior Complex, Elphinston Road, Khadki"},
                    new String[]{"Shevanta Cinema: Junnar", "Delhi Peth, Junnar, Near Old Bus Stop"},
                    new String[]{"Chhotu Maharaj Cine Cafe", "Vasuli Phata, Chakan MIDC"}
                );

                for (String[] data : puneCinemas) {
                    Cinema c = new Cinema();
                    c.setName(data[0]);
                    c.setAddress(data[1]);
                    c.setType("Multiplex");
                    c.setCity(pune);
                    cinemaRepo.save(c);
                }
                System.out.println("✅ Loaded " + puneCinemas.size() + " Cinemas for Pune.");
            }
         // 9. Populate Chennai Cinemas (From Screenshots)
            City chennai = cityRepo.findByName("Chennai");
            if (chennai != null) {
                List<String[]> chennaiCinemas = Arrays.asList(
                    // CENTRAL CHENNAI (T. Nagar, Royapettah, Egmore, Vadapalani)
                    new String[]{"KC (KrishnaveniCinemas) RG3 LASER", "No 3 Usman Road, T. Nagar, Chennai"},
                    new String[]{"AGS Cinemas: T. Nagar", "No: 24/1, GN Chetty Road, T. Nagar"},
                    new String[]{"PVR: Sathyam, Royapettah", "8, Thiru Vi Ka Road, Royapettah, Chennai"},
                    new String[]{"PVR: Palazzo, The Nexus Vijaya Mall", "3rd Floor, 183, Arcot Road, Vadapalani"},
                    new String[]{"INOX: Chennai Citi Centre, Dr. RK Salai", "3rd Floor, Chennai Citi Center, Doctor Radha Krishnan Salai"},
                    new String[]{"Woodlands Theatre: Chennai", "25, Westcott Road, Royapettah"},
                    new String[]{"INOX National: Arcot Road", "Chandra Metro Mall, Arcot Road, Aranganathan Nagar, Valasaravakkam"},
                    new String[]{"Kasi Talkies Dolby Atmos: Ashok Nagar", "No-4, Pillaiyar Koil St, Jafferkhanpet"},

                    // OMR & ECR (The IT Corridor)
                    new String[]{"AGS Cinemas OMR: Navlur", "Vivira Mall, 5th Floor, Navalur"},
                    new String[]{"INOX: The Marina Mall, OMR", "3rd Floor, The Marina Mall, Old Mahabalipuram Road, Egatoor"},
                    new String[]{"The Vijay Park Multiplex: Injambakkam ECR", "No.56, Anna Enclave, Injambakkam, Chennai"},
                    new String[]{"MAYAJAAL Multiplex: ECR, Chennai", "No. 34/1, East Coast Road, Kanathur"},
                    new String[]{"PVR: Heritage RSL ECR, Chennai", "1st Floor, Near Uthandi Toll Gate, Uthandi Village"},
                    new String[]{"Cinepolis: BSR Mall, OMR, Thoraipakkam", "BSR Mall, Thoraipakkam, Seevaram"},
                    new String[]{"GanapathyRam Theatre 4K Dolby 7.1", "101 Lattice Bridge Road, Adyar"},

                    // SOUTH CHENNAI (Velachery, Tambaram, Pallavaram)
                    new String[]{"INOX: LUXE Phoenix Market City, Velachery", "2nd Floor, Phoenix Market City, Velachery Main Road"},
                    new String[]{"PVR: Grand Mall, Velachery", "3rd Floor, Grand Square Mall, Velachery Road"},
                    new String[]{"PVR: Grand Galada, Pallavaram", "Grand Galada Mall, Kohinoor-2, Officers Line"},
                    new String[]{"Janatha Theatre 4K AC DTS (JBL AUDIO)", "Srinivasa Perumal Koil, Rajendra Nagar, Pallavaram"},
                    new String[]{"National Theatre 4K Dolby Atmos: Tambaram", "Rajaji Road, West Tambaram"},
                    new String[]{"Varadharaja Theatre: Chitlapakkam", "Chitlapakkam Main Road, Nehru Nagar"},
                    new String[]{"Medavakkam Kumaran Cinemas RGB LASER", "No. 4/347 Velachery Main Road, Medavakkam"},
                    new String[]{"Kumaran Theatre PROVA 4K DOLBY ATMOS", "No 364, Mount - Madipakkam Road, Ullagaram"},
                    new String[]{"Vetrivel RGB DOLBY: Nanganallur", "18, M.G.R. Road, Telegraph Colony, Nanganallur"},
                    new String[]{"Jothi Theatre 4K A/c DTS: ST Thomas Mount", "Alandur - Parangimalai, Parade Road"},

                    // NORTH & WEST CHENNAI (Anna Nagar, Koyambedu, Avadi)
                    new String[]{"PVR: VR Chennai, Anna Nagar", "3rd Floor, VR Mall, Metro Zone, No 44, Pillaiyar Koil Street"},
                    new String[]{"PVR: Ampa Mall, Nelson Manickam Road", "4th Floor, Ampa Skywalk Mall, Aminjikarai"},
                    new String[]{"Rohini Silver Screens: Koyambedu", "141/2, Poonamallee High Road, Koyembedu"},
                    new String[]{"AGS Cinemas: Villivakkam", "No. 1/1, Mettu Street Villivakkam"},
                    new String[]{"AGS Cinemas: Maduravoyal", "No 3/47, Alapakkam Main Road, Subramaniapuram"},
                    new String[]{"PVR: Aerohub, Chennai", "Aerohub East Wing, MLCP Block, Chennai International Airport"},
                    new String[]{"Green Cinemas 4K Atmos, PLF: Padi", "64, Periyar Nagar, Mes Colony, Chennai"},
                    new String[]{"Murugan Cinemas 4K: Ambattur", "Ambattur, Chennai"},
                    new String[]{"Remy Cinemas A/C DTS 2K 3D Laser: Avadi", "2nd Main Road, JB Estate, Chelliamman Kovil Street"},
                    new String[]{"Rakki Cinemas: Ambattur", "Ambattur, Chennai"},
                    new String[]{"Vela Cinemas RGB 4KLaser DolbyAtmos", "National Highway 205, Thiruninravur"},
                    new String[]{"Gokulam Cinemas 4K Dolby Atmos: Poonamallee", "795C, Old Sundar Theatre Complex, Rukmani Nagar"},
                    new String[]{"Vigneshwara Theatre RGB Laser: Poonamallee", "MG Nagar, Trunk Road, Opposite to Poonamallee fort"}
                );

                for (String[] data : chennaiCinemas) {
                    Cinema c = new Cinema();
                    c.setName(data[0]);
                    c.setAddress(data[1]);
                    c.setType("Multiplex");
                    c.setCity(chennai);
                    cinemaRepo.save(c);
                }
                System.out.println("✅ Loaded " + chennaiCinemas.size() + " Cinemas for Chennai.");
            }
         // 10. Populate Kolkata Cinemas (From Screenshots)
            City kolkata = cityRepo.findByName("Kolkata");
            if (kolkata != null) {
                List<String[]> kolCinemas = Arrays.asList(
                    // SOUTH & CENTRAL KOLKATA (The Heart of the City)
                    new String[]{"INOX: South City, Kolkata", "South City Mall, Prince Anwar Shah Road"},
                    new String[]{"Nandan: Kolkata", "1/1, A.J.C. Bose Road, Near West Bengal Bangla Academy"},
                    new String[]{"Cinepolis: Lake Mall, Kolkata", "Lake Mall, Rash Behari Avenue"},
                    new String[]{"INOX: Quest Mall", "Quest Mall, Syed Amir Ali Avenue, Park Circus"},
                    new String[]{"Cinepolis: Acropolis Mall, Kolkata", "Acropolis Mall, Rajdanga Main Road, Kasba"},
                    new String[]{"Navina Cinema: Tollygunge", "85, Prince Anwar Shah Road, Tollygunge"},
                    new String[]{"INOX: Forum Mall, Elgin Road", "10/3, Elgin Road, Bhawanipur"},
                    new String[]{"Priya Cinema: Rashbehari Avenue", "95, Rash Behari Avenue, Manoharpukur, Kalighat"},
                    new String[]{"INOX: Metro, Jawaharlal Nehru Road", "Metro Cinema, Jawaharlal Nehru Road, Esplanade"},
                    new String[]{"Menoka Cinema: Kolkata", "Sarat Chatterjee Avenue, Sarat Bose Road, Kalighat"},
                    new String[]{"Basusree Cinema: Kolkata", "102, S. P. Mukherjee Road, Kalighat"},
                    new String[]{"New Empire Cinema: Kolkata", "Humayun Place, New Market Area, Dharmatala"},
                    new String[]{"SSR Globe Cinemas: New Market", "Globe Mall, Lindsay Street, Dharmatala"},
                    new String[]{"Hind INOX: Kolkata", "Ganesh Chandra Avenue, Bow Bazaar"},
                    new String[]{"Prachi Cinema: Kolkata", "124/A, A.J.C Bose Road, Sealdah"},
                    new String[]{"Minar Cinema: Kolkata", "Bidhan Sarani, Shyam Bazar, Sovabazar"},
                    new String[]{"Bijoli Cinema: Kolkata", "Shyama Prasad Mukherjee Road, Bhawanipur"},
                    new String[]{"Binodini Theatre (Star Theatre)", "Bidhan Sarani, Beadon Street, Sovabazar"},

                    // SALT LAKE & NEW TOWN (IT Hubs)
                    new String[]{"INOX: City Centre II, Rajarhat", "City Center 2 Mall, New Town, Rajarhat"},
                    new String[]{"RDB Cinemas: Salt Lake", "Block EP-GP, Sector 5, Salt Lake"},
                    new String[]{"INOX: City Center, Salt Lake", "DC Block I, Sector-1, City Center Mall"},
                    new String[]{"Miraj Cinemas: The Terminus, New Town", "The Terminus Building, BG/12, AA-1B, New Town"},
                    new String[]{"Bioscope: Axis Mall, Rajarhat", "Axis Mall, CF Block, New Town"},
                    new String[]{"Nazrultirtha Cinema: Kolkata", "Service Road, New Town, Rajarhat"},
                    new String[]{"PVR: Mani Square Mall, Kolkata", "Mani Square Mall, EM Bypass Road"},
                    new String[]{"PVR: Uniworld Downtown Mall, New Town", "Uniworld City, Action Area III, New Town"},
                    new String[]{"Miraj Cinemas: Downtown Mall, Salt Lake", "IB Block, Sector 3, Salt Lake"},
                    new String[]{"INOX: Swabhumi, Maulana Azad Sarani", "89C, Moulana Abul Kalam Azad Sarani"},

                    // NORTH KOLKATA & SUBURBS (Barasat, Barrackpore, etc.)
                    new String[]{"PVR: Diamond Plaza, Jessore Kolkata", "Diamond City Mall, Jessore Road, Satgachi"},
                    new String[]{"INOX: Star Mall, Madhyamgram", "Star Mall, Jessore Road, Madhyamgram"},
                    new String[]{"Atindra Cinema: Barrackpore", "Ghosh Para Road, Near Barrackpore Railway Station"},
                    new String[]{"SSR Cinemas, Suncity Mall: Barasat", "Suncity Mall, Jessore Road, Champadali"},
                    new String[]{"Jaya Cinemas: City Mall, Barasat", "Jessore Road, Barasat, Near Duck Banglow"},
                    new String[]{"Rathindra Multiplex: Sodepur", "Rathindra Cinema Hall, Deshabondhu Nagar"},
                    new String[]{"Sonali Cinema: Dunlop", "Barrackpore Trunk Road, Narendra Nagar"},
                    new String[]{"Rupmandir Cinema: Belghoria", "Beehive Garden, Near Feeder Road"},
                    new String[]{"Jayanti Cinema: Barrackpore", "B.T Road, Barrackpore Chiriamore"},
                    new String[]{"Amala Cinema: Barrackpore", "Barrackpore - Barasat Road"},
                    new String[]{"Lali Cinema: Barasat", "Krishnanagar Road, Barasat"},
                    new String[]{"Padma Cinema: Kolkata", "Barasat Road, Sodepur"},

                    // HOWRAH & HOOGHLY SIDE
                    new String[]{"PVR: Avani, Kolkata", "Avani River Side Mall, Jagat Benarjee Ghat Road, Howrah"},
                    new String[]{"INOX: Forum Rangoli Mall, Belur", "Girish Ghosh Road, Belur, Howrah"},
                    new String[]{"SVF Cinemas: Platina Mall, Howrah", "Platina Mall, Nityadhan Mukherjee Road"},
                    new String[]{"Miraj Cinemas: Aurbindo Mall, Howrah", "Aurobino Mall, Sri Arabinda Road, Salkia"},

                    // SOUTH 24 PARGANAS & OUTSKIRTS (Behala, Baruipur, etc.)
                    new String[]{"INOX: Hiland Park", "Metropolis Mall, E.M. Bypass, Chak Garia"},
                    new String[]{"Asoka Cinema: Behala", "Diamond Harbour Road, Arcadia, Behala"},
                    new String[]{"SSR Ajanta Cinema: Behala", "Diamond Harbour Road, Pathak Para, Behala"},
                    new String[]{"SVF Cinemas: Wood Square Mall", "Wood Square Mall, Narendrapur"},
                    new String[]{"SVF Cinemas: Baruipur Show House", "Puratan Bazaar, Baruipur"},
                    new String[]{"Radha Studio: Tollygunge", "Deshpran Sasmal Road, Tollygunge"},
                    new String[]{"SSR Cinemas: Maheshtala", "Purti Plaza, Budge Budge Trunk Road"},
                    new String[]{"Utpal Dutta Mancha: Maheshtala", "Biren Roy Road W, Badamtala"},
                    new String[]{"Elora Multiplex: Champahati", "Baruipur - Champahati Road"},
                    new String[]{"Lila Cinema: Baruipur", "Baruipur - Champahati - Ghatakpukur Road"},
                    new String[]{"Uma Talkies: Bakhrahat", "Roypur Road, Bakhrahat"},
                    new String[]{"Sobha Talkies: Betberia", "Near Belegachi, Post Office, Betberia"}
                );

                for (String[] data : kolCinemas) {
                    Cinema c = new Cinema();
                    c.setName(data[0]);
                    c.setAddress(data[1]);
                    c.setType("Multiplex");
                    c.setCity(kolkata);
                    cinemaRepo.save(c);
                }
                System.out.println("✅ Loaded " + kolCinemas.size() + " Cinemas for Kolkata.");
            }
         // 11. Populate Kochi Cinemas (From Screenshots)
            City kochi = cityRepo.findByName("Kochi");
            if (kochi != null) {
                List<String[]> kochiCinemas = Arrays.asList(
                    // CITY CENTER (MG Road, Marine Drive, Ernakulam)
                    new String[]{"Cinepolis: Centre Square, Kochi", "6th floor, Centre Square Mall, M.G.Road, Ernakulam"},
                    new String[]{"Cinepolis: VIP Centre Square Mall, Kochi", "6th floor, Centre Square Mall, M.G.Road, Ernakulam"},
                    new String[]{"Shenoys: Kochi", "Shenoys Vistarama, M G Road, Cochin"},
                    new String[]{"Padma Cinema: Kochi", "Shenoy Cinemax, Level 1, MG Road, Kacheripady"},
                    new String[]{"Savita Cinema: Kochi", "41/32, Alberts College Road, Ernakulam"},
                    new String[]{"Kavitha Theatre 4K 3D Dolby 7.1: Ernakulam", "Paza Hotel, Mahatma Gandhi Road, Shenoys"},
                    new String[]{"Sridar: Marine Drive, Kochi", "Marine Drive, Shanmugham Road, Opposite Canara Bank"},

                    // EDAPPALLY, BYPASS & MARADU
                    new String[]{"PVR: Lulu, Kochi", "Lulu International Shopping Mall, Edappally, Nethaji Nagar"},
                    new String[]{"PVR: Oberon Mall, Kochi", "NH Bypass, Edapally Junction, Ernakulam"},
                    new String[]{"PVR: Forum Mall, Kochi", "3rd Floor, Forum Mall, NH-47, Vytilla Aroor Bypass"},
                    new String[]{"Vanitha Cineplex RGB Laser 4K 3D ATMOS: Edappally", "Edappally Toll Junction, Nethaji Nagar"},
                    new String[]{"PAN Cinemas Nucleus Mall 4K ATMOS", "Nucleus Mall, Maradu, Kochi-Madurai-Tondi Point Hwy"},

                    // ALUVA, ANGAMALY & PERUMBAVOOR
                    new String[]{"Matha Madhurya RGB Laser 3D Dolby Atmos: Aluva", "Periyar Nagar, T2/10, Perumbavoor Aluva Road"},
                    new String[]{"MY Cinemas, KSRT Complex, Angamaly", "5th Floor, KSRTC Complex, Angamaly"},
                    new String[]{"JM Movies Josh Mall: Mookkannoor Angamaly", "Mookkannoor, Ezhattumugham Road, Josh Mall"},
                    new String[]{"MY Cinemas RedCarpet: Kariyad", "National Highway 47, Nedumbassery, Near Cochin Airport"},
                    new String[]{"EVM Cinema A/C 4K RGB Laser 3D: Perumbavoor", "Old Kaala Vayal, Perumbavoor, Kochi"},
                    new String[]{"Aashirvad Cineplexx: Perumbavoor", "K.S.R.T.C Bus Stand Road, Perumbavoor"},
                    new String[]{"Casino Talkies A/C Real Laser 3D DOLBY 7.1: Aluva", "Little Flower Ln, Periyar Nagar, Aluva"},
                    new String[]{"Four Star Movies: Manjapra, Angamaly", "Canal Pass, Manjapra, Angamaly"},
                    new String[]{"Zeenath Theatre 2K A/C: Aluva", "Aluva, Periyar Nagar, Sub Jail Road"},

                    // NORTH PARAVUR, VYPIN & FORT KOCHI
                    new String[]{"Shafaz Laser & Grand 4K Theatre North Paravoor", "Chendamangalam Junction, North Paravur"},
                    new String[]{"Kairali Sree Theater: North Parvur", "North Paravur, Pullomkulam"},
                    new String[]{"G Cinemas Fort Kochi: 4K Dolby ATMOS", "Pandikudy, Anavathil, Thamaraparambu"},
                    new String[]{"Majestic Multiplex 4K RGB Laser: Narakkal", "Vypin - Pallipuram Road, Narakkal"},
                    new String[]{"K Cinemas 4K Dolby Atmos Tripplebeam 3D: Cherai", "Ayyampilly, Pallippuram Rd, Kochi"},

                    // TRIPUNITHURA & OTHERS
                    new String[]{"New Central Talkies RGB Laser 4K 3D Dolby Atmos", "New Central Talkies, Vaikom Road, Tripunithura"},
                    new String[]{"Central Talkies RGB Laser 4K 3D Dolby Atmos", "Central Talkies, Near Old Bus Stand, Tripunithura"},
                    new String[]{"M Cinemas 4K 3D Dolby ATMOS: Varapuzha", "Chettibhagam Main Rd, Varapuzha"},
                    new String[]{"J MAX 2K 3D Dolby 7.1: PATTIMATTOM", "Pattimattom PO, Pattimattom, Kerala"}
                );

                for (String[] data : kochiCinemas) {
                    Cinema c = new Cinema();
                    c.setName(data[0]);
                    c.setAddress(data[1]);
                    c.setType("Multiplex");
                    c.setCity(kochi);
                    cinemaRepo.save(c);
                }
                System.out.println("✅ Loaded " + kochiCinemas.size() + " Cinemas for Kochi.");
            }
        }
    }

    private void createCity(String name, String icon) {
        City c = new City();
        c.setName(name);
        c.setIconUrl(icon);
        cityRepo.save(c);
    }
}