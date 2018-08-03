<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Files App</title>
</head>
<body>

    <div class="header distance-padding-top distance-padding-bottom">
        <h1 class="text-center">Download a file and see the magic!</h1>
    </div>

    <div class="container">
        <div class="row">
            <div class="col-3 left-side-menu">
                <ul class="nav flex-column distance-margin-top">
                    <li class="nav-item">
                        <a href="#" class="nav-link active">File validator</a>
                    </li>
                    <li class="nav-item">
                        <a href="#" class="nav-link break-page">Do not click me!</a>
                    </li>
                </ul>
            </div>
            <div class="col-9">
                <form id="file-upload" action="/files-app/api/files" class="dropzone draggable-upload distance-margin-top">
                </form>
            </div>
        </div>
    </div>

</body>
</html>
