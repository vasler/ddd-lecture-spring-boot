package vasler.dddlecture.adapters.primary.htmx;

public class HtmlFragments {
        public static final String PAGE_HEADER = """
            <!DOCTYPE html>
            <html>
                <head>
                    <meta charset="UTF-8" />
                    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
                    <script src="https://unpkg.com/htmx.org@1.9.10" integrity="sha384-D1Kt99CQMDuVetoL1lrYwg5t+9QdHe7NLX/SoJYkXDFfX37iInKRy5xLSi8nO7UC" crossorigin="anonymous"></script>
                    <script src="https://cdn.tailwindcss.com"></script>
                    <title>DDD Lecture</title>
                </head>
                <body class="bg-gray-100">
                        <div id="page-content" class="bg-white p-4 mt-2 border rounded-lg max-w-2xl m-auto">""";

    public static final String PAGE_FOOTER = """
                        </div>
                </body>
            </html>""";
}
