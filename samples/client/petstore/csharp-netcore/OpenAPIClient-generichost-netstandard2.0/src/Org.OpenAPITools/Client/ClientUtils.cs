/*
 * OpenAPI Petstore
 *
 * This spec is mainly for testing Petstore server and contains fake endpoints, models. Please do not use this for any other purpose. Special characters: \" \\
 *
 * The version of the OpenAPI document: 1.0.0
 * Generated by: https://github.com/openapitools/openapi-generator.git
 */

using System;
using System.IO;
using System.Linq;
using System.Net.Http;
using System.Text;
using System.Text.Json;
using System.Text.RegularExpressions;
using Microsoft.Extensions.Hosting;
using Microsoft.Extensions.DependencyInjection;
using Polly.Timeout;
using Polly.Extensions.Http;
using Polly;
using Org.OpenAPITools.Api;
using KellermanSoftware.CompareNetObjects;

namespace Org.OpenAPITools.Client
{
    /// <summary>
    /// Utility functions providing some benefit to API client consumers.
    /// </summary>
    public static class ClientUtils
    {
        /// <summary>
        /// An instance of CompareLogic.
        /// </summary>
        public static CompareLogic compareLogic;

        /// <summary>
        /// Static constructor to initialise compareLogic.
        /// </summary>
        static ClientUtils()
        {
            compareLogic = new CompareLogic();
        }

        /// <summary>
        /// A delegate for events.
        /// </summary>
        /// <typeparam name="T"></typeparam>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        /// <returns></returns>
        public delegate void EventHandler<T>(object sender, T e) where T : EventArgs;

        /// <summary>
        /// Returns true when deserialization succeeds.
        /// </summary>
        /// <typeparam name="T"></typeparam>
        /// <param name="json"></param>
        /// <param name="options"></param>
        /// <param name="result"></param>
        /// <returns></returns>
        public static bool TryDeserialize<T>(string json, JsonSerializerOptions options, out T result)
        {
            try
            {
                result = JsonSerializer.Deserialize<T>(json, options);
                return result != null;
            }
            catch (Exception)
            {
                result = default;
                return false;
            }
        }

        /// <summary>
        /// Returns true when deserialization succeeds.
        /// </summary>
        /// <typeparam name="T"></typeparam>
        /// <param name="reader"></param>
        /// <param name="options"></param>
        /// <param name="result"></param>
        /// <returns></returns>
        public static bool TryDeserialize<T>(ref Utf8JsonReader reader, JsonSerializerOptions options, out T result)
        {
            try
            {
                result = JsonSerializer.Deserialize<T>(ref reader, options);
                return result != null;
            }
            catch (Exception)
            {
                result = default;
                return false;
            }
        }

        /// <summary>
        /// Sanitize filename by removing the path
        /// </summary>
        /// <param name="filename">Filename</param>
        /// <returns>Filename</returns>
        public static string SanitizeFilename(string filename)
        {
            Match match = Regex.Match(filename, @".*[/\\](.*)$");
            return match.Success ? match.Groups[1].Value : filename;
        }

        /// <summary>
        /// If parameter is DateTime, output in a formatted string (default ISO 8601), customizable with Configuration.DateTime.
        /// If parameter is a list, join the list with ",".
        /// Otherwise just return the string.
        /// </summary>
        /// <param name="obj">The parameter (header, path, query, form).</param>
        /// <param name="format">The DateTime serialization format.</param>
        /// <returns>Formatted string.</returns>
        public static string ParameterToString(object obj, string format = ISO8601_DATETIME_FORMAT)
        {
            if (obj is DateTime dateTime)
                // Return a formatted date string - Can be customized with Configuration.DateTimeFormat
                // Defaults to an ISO 8601, using the known as a Round-trip date/time pattern ("o")
                // https://msdn.microsoft.com/en-us/library/az4se3k1(v=vs.110).aspx#Anchor_8
                // For example: 2009-06-15T13:45:30.0000000
                return dateTime.ToString(format);
            if (obj is DateTimeOffset dateTimeOffset)
                // Return a formatted date string - Can be customized with Configuration.DateTimeFormat
                // Defaults to an ISO 8601, using the known as a Round-trip date/time pattern ("o")
                // https://msdn.microsoft.com/en-us/library/az4se3k1(v=vs.110).aspx#Anchor_8
                // For example: 2009-06-15T13:45:30.0000000
                return dateTimeOffset.ToString(format);
            if (obj is bool boolean)
                return boolean ? "true" : "false";
            if (obj is System.Collections.ICollection collection)
            {
                var stringCollection = new ArrayList();
                foreach (var item in collection)
                {
                    stringCollection.Add(Convert.ToString(item, CultureInfo.InvariantCulture));
                }
                return string.Join(",", stringCollection.ToArray());
            }

            return Convert.ToString(obj, System.Globalization.CultureInfo.InvariantCulture);
        }

        /// <summary>
        /// URL encode a string
        /// Credit/Ref: https://github.com/restsharp/RestSharp/blob/master/RestSharp/Extensions/StringExtensions.cs#L50
        /// </summary>
        /// <param name="input">string to be URL encoded</param>
        /// <returns>Byte array</returns>
        public static string UrlEncode(string input)
        {
            const int maxLength = 32766;

            if (input == null)
            {
                throw new ArgumentNullException("input");
            }

            if (input.Length <= maxLength)
            {
                return Uri.EscapeDataString(input);
            }

            StringBuilder sb = new StringBuilder(input.Length * 2);
            int index = 0;

            while (index < input.Length)
            {
                int length = Math.Min(input.Length - index, maxLength);
                string subString = input.Substring(index, length);

                sb.Append(Uri.EscapeDataString(subString));
                index += subString.Length;
            }

            return sb.ToString();
        }

        /// <summary>
        /// Encode string in base64 format.
        /// </summary>
        /// <param name="text">string to be encoded.</param>
        /// <returns>Encoded string.</returns>
        public static string Base64Encode(string text)
        {
            return Convert.ToBase64String(System.Text.Encoding.UTF8.GetBytes(text));
        }

        /// <summary>
        /// Convert stream to byte array
        /// </summary>
        /// <param name="inputStream">Input stream to be converted</param>
        /// <returns>Byte array</returns>
        public static byte[] ReadAsBytes(Stream inputStream)
        {
            using (var ms = new MemoryStream())
            {
                inputStream.CopyTo(ms);
                return ms.ToArray();
            }
        }

        /// <summary>
        /// Select the Content-Type header's value from the given content-type array:
        /// if JSON type exists in the given array, use it;
        /// otherwise use the first one defined in 'consumes'
        /// </summary>
        /// <param name="contentTypes">The Content-Type array to select from.</param>
        /// <returns>The Content-Type header to use.</returns>
        public static string SelectHeaderContentType(string[] contentTypes)
        {
            if (contentTypes.Length == 0)
                return null;

            foreach (var contentType in contentTypes)
            {
                if (IsJsonMime(contentType))
                    return contentType;
            }

            return contentTypes[0]; // use the first content type specified in 'consumes'
        }

        /// <summary>
        /// Select the Accept header's value from the given accepts array:
        /// if JSON exists in the given array, use it;
        /// otherwise use all of them (joining into a string)
        /// </summary>
        /// <param name="accepts">The accepts array to select from.</param>
        /// <returns>The Accept header to use.</returns>
        public static string SelectHeaderAccept(string[] accepts)
        {
            if (accepts.Length == 0)
                return null;

            if (accepts.Contains("application/json", StringComparer.OrdinalIgnoreCase))
                return "application/json";

            return string.Join(",", accepts);
        }

        /// <summary>
        /// Provides a case-insensitive check that a provided content type is a known JSON-like content type.
        /// </summary>
        public static readonly Regex JsonRegex = new Regex("(?i)^(application/json|[^;/ \t]+/[^;/ \t]+[+]json)[ \t]*(;.*)?$");

        /// <summary>
        /// Check if the given MIME is a JSON MIME.
        /// JSON MIME examples:
        ///    application/json
        ///    application/json; charset=UTF8
        ///    APPLICATION/JSON
        ///    application/vnd.company+json
        /// </summary>
        /// <param name="mime">MIME</param>
        /// <returns>Returns True if MIME type is json.</returns>
        public static bool IsJsonMime(string mime)
        {
            if (string.IsNullOrWhiteSpace(mime)) return false;

            return JsonRegex.IsMatch(mime) || mime.Equals("application/json-patch+json");
        }

        /// <summary>
        /// The base path of the API
        /// </summary>
        public const string BASE_ADDRESS = "http://petstore.swagger.io:80/v2";

        /// <summary>
        /// The scheme of the API
        /// </summary>
        public const string SCHEME = "http";

        /// <summary>
        /// The context path of the API
        /// </summary>
        public const string CONTEXT_PATH = "/v2";

        /// <summary>
        /// The host of the API
        /// </summary>
        public const string HOST = "petstore.swagger.io";

        /// <summary>
        /// The format to use for DateTime serialization
        /// </summary>
        public const string ISO8601_DATETIME_FORMAT = "o";

        /// <summary>
        /// Add the api to your host builder.
        /// </summary>
        /// <param name="builder"></param>
        /// <param name="options"></param>
        public static IHostBuilder ConfigureApi(this IHostBuilder builder, Action<HostBuilderContext, HostConfiguration> options)
        {
            builder.ConfigureServices((context, services) => 
            {
                HostConfiguration config = new HostConfiguration(services);

                options(context, config);

                AddApi(services, config);
            });

            return builder;
        }

        /// <summary>
        /// Add the api to your host builder.
        /// </summary>
        /// <param name="services"></param>
        /// <param name="options"></param>
        public static void AddApi(this IServiceCollection services, Action<HostConfiguration> options)
        {
            HostConfiguration config = new HostConfiguration(services);
            options(config);
            AddApi(services, config);
        }

        private static void AddApi(IServiceCollection services, HostConfiguration host)
        {
            if (!host.HttpClientsAdded)
                host.AddApiHttpClients();

            // ensure that a token provider was provided for this token type
            // if not, default to RateLimitProvider
            var containerServices = services.Where(s => s.ServiceType.IsGenericType &&
                s.ServiceType.GetGenericTypeDefinition().IsAssignableFrom(typeof(TokenContainer<>))).ToArray();

            foreach(var containerService in containerServices)
            {
                var tokenType = containerService.ServiceType.GenericTypeArguments[0];

                var provider = services.FirstOrDefault(s => s.ServiceType.IsAssignableFrom(typeof(TokenProvider<>).MakeGenericType(tokenType)));

                if (provider == null)
                {
                    services.AddSingleton(typeof(RateLimitProvider<>).MakeGenericType(tokenType));
                    services.AddSingleton(typeof(TokenProvider<>).MakeGenericType(tokenType), 
                        s => s.GetRequiredService(typeof(RateLimitProvider<>).MakeGenericType(tokenType)));
                }
            }
        }

        /// <summary>
        /// Adds a Polly retry policy to your clients.
        /// </summary>
        /// <param name="client"></param>
        /// <param name="retries"></param>
        /// <returns></returns>
        public static IHttpClientBuilder AddRetryPolicy(this IHttpClientBuilder client, int retries)
        {
            client.AddPolicyHandler(RetryPolicy(retries));

            return client;
        }

        /// <summary>
        /// Adds a Polly timeout policy to your clients.
        /// </summary>
        /// <param name="client"></param>
        /// <param name="timeout"></param>
        /// <returns></returns>
        public static IHttpClientBuilder AddTimeoutPolicy(this IHttpClientBuilder client, TimeSpan timeout)
        {
            client.AddPolicyHandler(TimeoutPolicy(timeout));

            return client;
        }

        /// <summary>
        /// Adds a Polly circiut breaker to your clients.
        /// </summary>
        /// <param name="client"></param>
        /// <param name="handledEventsAllowedBeforeBreaking"></param>
        /// <param name="durationOfBreak"></param>
        /// <returns></returns>
        public static IHttpClientBuilder AddCircuitBreakerPolicy(this IHttpClientBuilder client, int handledEventsAllowedBeforeBreaking, TimeSpan durationOfBreak)
        {
            client.AddTransientHttpErrorPolicy(builder => CircuitBreakerPolicy(builder, handledEventsAllowedBeforeBreaking, durationOfBreak));

            return client;
        }

        private static Polly.Retry.AsyncRetryPolicy<HttpResponseMessage> RetryPolicy(int retries)
            => HttpPolicyExtensions
                .HandleTransientHttpError()
                .Or<TimeoutRejectedException>()
                .RetryAsync(retries);

        private static AsyncTimeoutPolicy<HttpResponseMessage> TimeoutPolicy(TimeSpan timeout)
            => Policy.TimeoutAsync<HttpResponseMessage>(timeout);

        private static Polly.CircuitBreaker.AsyncCircuitBreakerPolicy<HttpResponseMessage> CircuitBreakerPolicy(
            PolicyBuilder<HttpResponseMessage> builder, int handledEventsAllowedBeforeBreaking, TimeSpan durationOfBreak)
                => builder.CircuitBreakerAsync(handledEventsAllowedBeforeBreaking, durationOfBreak);
    }
}
