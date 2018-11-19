<#import "/spring.ftl" as spring/>
<html>
<head>
    <style>
        *, :before, :after {
            box-sizing: border-box;
        }

        body {
            display: flex;
            align-items: center;
            background: #dff3fd;
            min-width: 275px;
            height: 100vh;
            margin: 0 10vw;
            overflow: hidden;
            color: #2677f2;
        }

        h1 {
            font-size: 2em;
        }

        div.buttons {
            margin-left: 2em;
        }

        a {
            display: inline-block;
            padding: .6em .8em;
            margin-right: 1em;
            margin-bottom: 1em;
            border: 3px solid #aecffb;
            color: #2677f2;
            font-weight: 500;
            text-transform: uppercase;
            text-decoration: none;
            letter-spacing: .2em;
            position: relative;
            overflow: hidden;
            transition: .3s;
        }

        a:before {
            content: '';
            background: #2677f2;
            height: 100%;
            width: 100%;
            position: absolute;
            top: -100%;
            left: 0;
            transition: .3s;
            z-index: -1;
        }

        a:hover {
            color: #dff3fd;
        }

        a:hover:before {
            top: 0;
        }
    </style>
</head>
<body>
<h1>Data Returns API Landing Page</h1>
<div class="buttons">
    <div>
        <a href="swagger-ui.html">Swagger Browser</a>
    </div>
    <div>
        <a href="v2/api-docs">Swagger JSON File</a>
    </div>
    <div>
        <a href="api/">HAL Browser</a>
    </div>
    <div>
        <a href="api/profile">ALPS Profile</a>
    </div>
</div>
</body>
</html>
