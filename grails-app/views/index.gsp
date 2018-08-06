<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title><g:message code="com.ivan.title" /></title>
</head>
<body>

    <div class="header distance-padding-top distance-padding-bottom">
        <h1 class="text-center"><g:message code="com.ivan.page.heading" /></h1>
    </div>

    <div class="container">
        <div class="row">
            <div class="col-3 left-side-menu">
                <ul class="nav flex-column distance-margin-top">
                    <li class="nav-item">
                        <a href="#" class="nav-link active"><g:message code="com.ivan.leftSideMenu.validator" /></a>
                    </li>
                    <li class="nav-item">
                        <a href="#" class="nav-link break-page"><g:message code="com.ivan.leftSideMenu.doNoClick" /></a>
                    </li>
                </ul>
            </div>
            <div class="col-9">
                <form id="file-upload" action="/files-app/api/files" class="dropzone draggable-upload distance-margin-top">
                    <div id="spinner" class="spinner" style="display:none;"></div>
                </form>
            </div>
        </div>
    </div>

    <div id="resultPopup" class="display-none" title="...">...</div>

</body>
</html>
