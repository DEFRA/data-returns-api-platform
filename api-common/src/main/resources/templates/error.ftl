<#import "/spring.ftl" as spring/>
<html>
<head>
    <title>Error - ${status}</title>
    <style>
        *, :before, :after {
            box-sizing: border-box;
        }

        body {
            display: flex;
            align-items: center;
            background: #dff3fd;
            min-width: 275px;
            margin: 0 10vw;
            color: #2677f2;
        }

        .wrapper {
            flex-grow: 2;
            width: 40vw;
            max-width: 500px;
            margin: 0 auto;
        }

        h1 {
            font-size: 3em;
        }

        p {
            width: 95%;
            font-size: 1.5em;
            line-height: 1.4;
        }

        th {
            text-align: left;
            font-weight: normal;
        }

        td {
            padding-left: 3em;
        }

        div.buttons {
            margin-top: 1em;
            white-space: nowrap;
            display: inline-block;
        }

        div.buttons a {
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

        div.buttons a:before {
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

        div.buttons a:hover {
            color: #dff3fd;
        }

        div.buttons a:hover:before {
            top: 0;
        }
    </style>
</head>
<body>
<div class="wrapper">
    <h1>Error - ${status} ${error}</h1>
    <p>Something went wrong...</p>
    <table>
        <tr>
            <th>Path</th>
            <td>${path}</td>
        </tr>
        <tr>
            <th>Message</th>
            <td>${message}</td>
        </tr>
        <tr>
            <th>Date</th>
            <td>${timestamp?datetime}</td>
        </tr>
<#if exception?has_content>
        <tr>
            <th>Exception</th>
            <td>${exception}</td>
        </tr>
</#if>
<#if trace?has_content>
        <tr>
            <th>Trace</th>
            <td>
                <pre>${trace}</pre>
            </td>
        </tr>
</#if>
    </table>
    <div class="buttons">
        <a href="/">Home</a>
    </div>
</div>
</body>
</html>
