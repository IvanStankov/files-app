class UrlMappings {

	static mappings = {
        "/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }

        "/api/files"(resources: "file")

        "/"(view:"/index")
        "500"(view:'/error')
	}
}
