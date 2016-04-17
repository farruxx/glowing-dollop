# glowing-dollop
        Yandex's cnd server don't allow cache
    because, download url redirects to random static url, and OkHttpClient
    caches the destination url.

    So, every time we are caching a random url and it never been cached with standard
    cache headers.
        
    Here the header "Last-Modified" will cached. If backend developers fix it, 
    it will work..
