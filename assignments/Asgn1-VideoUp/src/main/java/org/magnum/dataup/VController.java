package org.magnum.dataup;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import javax.servlet.http.HttpServletRequest;

import org.magnum.dataup.model.Video;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;

@Controller
public class VController {

	 private static final AtomicLong currentId = new AtomicLong(0L);		
	 private Map<Long,Video> videos = new HashMap<Long, Video>();
	
	@RequestMapping(value = VideoSvcApi.VIDEO_SVC_PATH, method = RequestMethod.GET)
	@ResponseBody
	public Collection getVideoList() {
	//	Map<Long, Video> videos = new HashMap<Long, Video>();
		return videos.values();		
	}
	
	@RequestMapping(value = VideoSvcApi.VIDEO_SVC_PATH, method = RequestMethod.POST)
	@ResponseBody
	public Video addVideo(@RequestBody Video v){	
		save(v);		
		return v;
	}
	
	
	
	
	 public Video save(Video entity) {
        checkAndSetId(entity);
        videos.put(entity.getId(), entity);
        return entity;

}


	 
	 

private void checkAndSetId(Video entity) {
        if(entity.getId() == 0){
                    entity.setId(currentId.incrementAndGet());
        }

}


	 private String getDataUrl(long videoId){
        String url = getUrlBaseForLocalServer() + "/video/" + videoId + "/data";
        return url;
    }
	 
		private String getUrlBaseForLocalServer() {
			   HttpServletRequest request = 
					   ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
			   String base = 
			      "http://"+request.getServerName() 
			      + ((request.getServerPort() != 80) ? ":"+request.getServerPort() : "");
			   return base;
			}
}
