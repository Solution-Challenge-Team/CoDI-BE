package koders.codi.domain.jobInfo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
public class JobInfo {
    private String company;
    private String subject;
    private String location;
    private String endDate;
    private String url;
}
