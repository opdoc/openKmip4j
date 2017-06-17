package hu.opdoc.openkmip4j.primitives;

import hu.opdoc.openkmip4j.Version;

import java.lang.Integer;
import java.util.HashMap;
import java.util.Map;

import static hu.opdoc.openkmip4j.Version.*;

/**
 * Created by peter on 2017.06.17..
 */
public enum StandardTag implements Tag {
    // KMIP 1.0
    ActivationDate(KMIP_1_0, 0x420001),
    ApplicationData(KMIP_1_0, 0x420002),
    ApplicationNamespace(KMIP_1_0, 0x420003),
    ApplicationSpecificInformation(KMIP_1_0, 0x420004),
    ArchiveDate(KMIP_1_0, 0x420005),
    AsynchronousCorrelationValue(KMIP_1_0, 0x420006),
    AsynchronousIndicator(KMIP_1_0, 0x420007),
    Attribute(KMIP_1_0, 0x420008),
    AttributeIndex(KMIP_1_0, 0x420009),
    AttributeName(KMIP_1_0, 0x42000A),
    AttributeValue(KMIP_1_0, 0x42000B),
    Authentication(KMIP_1_0, 0x42000C),
    BatchCount(KMIP_1_0, 0x42000D),
    BatchErrorContinuationOption(KMIP_1_0, 0x42000E),
    BatchItem(KMIP_1_0, 0x42000F),
    BatchOrderOption(KMIP_1_0, 0x420010),
    BlockCipherMode(KMIP_1_0, 0x420011),
    CancellationResult(KMIP_1_0, 0x420012),
    Certificate(KMIP_1_0, 0x420013),
    CertificateIdentifier(KMIP_1_0, 0x420014, KMIP_1_1),
    CertificateIssuer(KMIP_1_0, 0x420015, KMIP_1_1),
    CertificateIssuerAlternativeName(KMIP_1_0, 0x420016, KMIP_1_1),
    CertificateIssuerDistinguishedName(KMIP_1_0, 0x420017, KMIP_1_1),
    CertificateRequest(KMIP_1_0, 0x420018),
    CertificateRequestType(KMIP_1_0, 0x420019),
    CertificateSubject(KMIP_1_0, 0x42001A, KMIP_1_1),
    CertificateSubjectAlternativeName(KMIP_1_0, 0x42001B, KMIP_1_1),
    CertificateSubjectDistinguishedName(KMIP_1_0, 0x42001C, KMIP_1_1),
    CertificateType(KMIP_1_0, 0x42001D),
    CertificateValue(KMIP_1_0, 0x42001E),
    CommonTemplateAttribute(KMIP_1_0, 0x42001F),
    CompromiseDate(KMIP_1_0, 0x420020),
    CompromiseOccurrenceDate(KMIP_1_0, 0x420021),
    ContactInformation(KMIP_1_0, 0x420022),
    Credential(KMIP_1_0, 0x420023),
    CredentialType(KMIP_1_0, 0x420024),
    CredentialValue(KMIP_1_0, 0x420025),
    CriticalityIndicator(KMIP_1_0, 0x420026),
    CRTCoefficient(KMIP_1_0, 0x420027),
    CryptographicAlgorithm(KMIP_1_0, 0x420028),
    CryptographicDomainParameters(KMIP_1_0, 0x420029),
    CryptographicLength(KMIP_1_0, 0x42002A),
    CryptographicParameters(KMIP_1_0, 0x42002B),
    CryptographicUsageMask(KMIP_1_0, 0x42002C),
    CustomAttribute(KMIP_1_0, 0x42002D),
    D(KMIP_1_0, 0x42002E),
    DeactivationDate(KMIP_1_0, 0x42002F),
    DerivationData(KMIP_1_0, 0x420030),
    DerivationMethod(KMIP_1_0, 0x420031),
    DerivationParameters(KMIP_1_0, 0x420032),
    DestroyDate(KMIP_1_0, 0x420033),
    Digest(KMIP_1_0, 0x420034),
    DigestValue(KMIP_1_0, 0x420035),
    EncryptionKeyInformation(KMIP_1_0, 0x420036),
    G(KMIP_1_0, 0x420037),
    HashingAlgorithm(KMIP_1_0, 0x420038),
    InitialDate(KMIP_1_0, 0x420039),
    InitializationVector(KMIP_1_0, 0x42003A),
    Issuer(KMIP_1_0, 0x42003B, KMIP_1_1),
    IterationCount(KMIP_1_0, 0x42003C),
    IVCounterNonce(KMIP_1_0, 0x42003D),
    J(KMIP_1_0, 0x42003E),
    Key(KMIP_1_0, 0x42003F),
    KeyBlock(KMIP_1_0, 0x420040),
    KeyCompressionType(KMIP_1_0, 0x420041),
    KeyFormatType(KMIP_1_0, 0x420042),
    KeyMaterial(KMIP_1_0, 0x420043),
    KeyPartIdentifier(KMIP_1_0, 0x420044),
    KeyValue(KMIP_1_0, 0x420045),
    KeyWrappingData(KMIP_1_0, 0x420046),
    KeyWrappingSpecification(KMIP_1_0, 0x420047),
    LastChangeDate(KMIP_1_0, 0x420048),
    LeaseTime(KMIP_1_0, 0x420049),
    Link(KMIP_1_0, 0x42004A),
    LinkType(KMIP_1_0, 0x42004B),
    LinkedObjectIdentifier(KMIP_1_0, 0x42004C),
    MACSignature(KMIP_1_0, 0x42004D),
    MACSignatureKeyInformation(KMIP_1_0, 0x42004E),
    MaximumItems(KMIP_1_0, 0x42004F),
    MaximumResponseSize(KMIP_1_0, 0x420050),
    MessageExtension(KMIP_1_0, 0x420051),
    Modulus(KMIP_1_0, 0x420052),
    Name(KMIP_1_0, 0x420053),
    NameType(KMIP_1_0, 0x420054),
    NameValue(KMIP_1_0, 0x420055),
    ObjectGroup(KMIP_1_0, 0x420056),
    ObjectType(KMIP_1_0, 0x420057),
    Offset(KMIP_1_0, 0x420058),
    OpaqueDataType(KMIP_1_0, 0x420059),
    OpaqueDataValue(KMIP_1_0, 0x42005A),
    OpaqueObject(KMIP_1_0, 0x42005B),
    Operation(KMIP_1_0, 0x42005C),
    OperationPolicyName(KMIP_1_0, 0x42005D, KMIP_1_3),
    P(KMIP_1_0, 0x42005E),
    PaddingMethod(KMIP_1_0, 0x42005F),
    PrimeExponentP(KMIP_1_0, 0x420060),
    PrimeExponentQ(KMIP_1_0, 0x420061),
    PrimeFieldSize(KMIP_1_0, 0x420062),
    PrivateExponent(KMIP_1_0, 0x420063),
    PrivateKey(KMIP_1_0, 0x420064),
    PrivateKeyTemplateAttribute(KMIP_1_0, 0x420065),
    PrivateKeyUniqueIdentifier(KMIP_1_0, 0x420066),
    ProcessStartDate(KMIP_1_0, 0x420067),
    ProtectStopDate(KMIP_1_0, 0x420068),
    ProtocolVersion(KMIP_1_0, 0x420069),
    ProtocolVersionMajor(KMIP_1_0, 0x42006A),
    ProtocolVersionMinor(KMIP_1_0, 0x42006B),
    PublicExponent(KMIP_1_0, 0x42006C),
    PublicKey(KMIP_1_0, 0x42006D),
    PublicKeyTemplateAttribute(KMIP_1_0, 0x42006E),
    PublicKeyUniqueIdentifier(KMIP_1_0, 0x42006F),
    PutFunction(KMIP_1_0, 0x420070),
    Q(KMIP_1_0, 0x420071),
    QString(KMIP_1_0, 0x420072),
    Qlength(KMIP_1_0, 0x420073),
    QueryFunction(KMIP_1_0, 0x420074),
    RecommendedCurve(KMIP_1_0, 0x420075),
    ReplacedUniqueIdentifier(KMIP_1_0, 0x420076),
    RequestHeader(KMIP_1_0, 0x420077),
    RequestMessage(KMIP_1_0, 0x420078),
    RequestPayload(KMIP_1_0, 0x420079),
    ResponseHeader(KMIP_1_0, 0x42007A),
    ResponseMessage(KMIP_1_0, 0x42007B),
    ResponsePayload(KMIP_1_0, 0x42007C),
    ResultMessage(KMIP_1_0, 0x42007D),
    ResultReason(KMIP_1_0, 0x42007E),
    ResultStatus(KMIP_1_0, 0x42007F),
    RevocationMessage(KMIP_1_0, 0x420080),
    RevocationReason(KMIP_1_0, 0x420081),
    RevocationReasonCode(KMIP_1_0, 0x420082),
    KeyRoleType(KMIP_1_0, 0x420083),
    Salt(KMIP_1_0, 0x420084),
    SecretData(KMIP_1_0, 0x420085),
    SecretDataType(KMIP_1_0, 0x420086),
    SerialNumber(KMIP_1_0, 0x420087, KMIP_1_1),
    ServerInformation(KMIP_1_0, 0x420088),
    SplitKey(KMIP_1_0, 0x420089),
    SplitKeyMethod(KMIP_1_0, 0x42008A),
    SplitKeyParts(KMIP_1_0, 0x42008B),
    SplitKeyThreshold(KMIP_1_0, 0x42008C),
    State(KMIP_1_0, 0x42008D),
    StorageStatusMask(KMIP_1_0, 0x42008E),
    SymmetricKey(KMIP_1_0, 0x42008F),
    Template(KMIP_1_0, 0x420090),
    TemplateAttribute(KMIP_1_0, 0x420091),
    TimeStamp(KMIP_1_0, 0x420092),
    UniqueBatchItemID(KMIP_1_0, 0x420093),
    UniqueIdentifier(KMIP_1_0, 0x420094),
    UsageLimits(KMIP_1_0, 0x420095),
    UsageLimitsCount(KMIP_1_0, 0x420096),
    UsageLimitsTotal(KMIP_1_0, 0x420097),
    UsageLimitsUnit(KMIP_1_0, 0x420098),
    Username(KMIP_1_0, 0x420099),
    ValidityDate(KMIP_1_0, 0x42009A),
    ValidityIndicator(KMIP_1_0, 0x42009B),
    VendorExtension(KMIP_1_0, 0x42009C),
    VendorIdentification(KMIP_1_0, 0x42009D),
    WrappingMethod(KMIP_1_0, 0x42009E),
    X(KMIP_1_0, 0x42009F),
    Y(KMIP_1_0, 0x4200A0),
    Password(KMIP_1_0, 0x4200A1),

    // KMIP 1.1
    DeviceIdentifier(KMIP_1_1, 0x4200A2),
    EncodingOption(KMIP_1_1, 0x4200A3),
    ExtensionInformation(KMIP_1_1, 0x4200A4),
    ExtensionName(KMIP_1_1, 0x4200A5),
    ExtensionTag(KMIP_1_1, 0x4200A6),
    ExtensionType(KMIP_1_1, 0x4200A7),
    Fresh(KMIP_1_1, 0x4200A8),
    MachineIdentifier(KMIP_1_1, 0x4200A9),
    MediaIdentifier(KMIP_1_1, 0x4200AA),
    NetworkIdentifier(KMIP_1_1, 0x4200AB),
    ObjectGroupMember(KMIP_1_1, 0x4200AC),
    CertificateLength(KMIP_1_1, 0x4200AD),
    DigitalSignatureAlgorithm(KMIP_1_1, 0x4200AE),
    CertificateSerialNumber(KMIP_1_1, 0x4200AF),
    DeviceSerialNumber(KMIP_1_1, 0x4200B0),
    IssuerAlternativeName(KMIP_1_1, 0x4200B1),
    IssuerDistinguishedName(KMIP_1_1, 0x4200B2),
    SubjectAlternativeName(KMIP_1_1, 0x4200B3),
    SubjectDistinguishedName(KMIP_1_1, 0x4200B4),
    X509CertificateIdentifier(KMIP_1_1, 0x4200B5),
    X509CertificateIssuer(KMIP_1_1, 0x4200B6),
    X509CertificateSubject(KMIP_1_1, 0x4200B7),

    // KMIP 1.2
    KeyValueLocation(KMIP_1_2, 0x4200B8),
    KeyValueLocationValue(KMIP_1_2, 0x4200B9),
    KeyValueLocationType(KMIP_1_2, 0x4200BA),
    KeyValuePresent(KMIP_1_2, 0x4200BB),
    OriginalCreationDate(KMIP_1_2, 0x4200BC),
    PGPKey(KMIP_1_2, 0x4200BD),
    PGPKeyVersion(KMIP_1_2, 0x4200BE),
    AlternativeName(KMIP_1_2, 0x4200BF),
    AlternativeNameValue(KMIP_1_2, 0x4200C0),
    AlternativeNameType(KMIP_1_2, 0x4200C1),
    Data(KMIP_1_2, 0x4200C2),
    SignatureData(KMIP_1_2, 0x4200C3),
    DataLength(KMIP_1_2, 0x4200C4),
    RandomIV(KMIP_1_2, 0x4200C5),
    MACData(KMIP_1_2, 0x4200C6),
    AttestationType(KMIP_1_2, 0x4200C7),
    Nonce(KMIP_1_2, 0x4200C8),
    NonceID(KMIP_1_2, 0x4200C9),
    NonceValue(KMIP_1_2, 0x4200CA),
    AttestationMeasurement(KMIP_1_2, 0x4200CB),
    AttestationAssertion(KMIP_1_2, 0x4200CC),
    IVLength(KMIP_1_2, 0x4200CD),
    TagLength(KMIP_1_2, 0x4200CE),
    FixedFieldLength(KMIP_1_2, 0x4200CF),
    CounterLength(KMIP_1_2, 0x4200D0),
    InitialCounterValue(KMIP_1_2, 0x4200D1),
    InvocationFieldLength(KMIP_1_2, 0x4200D2),
    AttestationCapableIndicator(KMIP_1_2, 0x4200D3),

    // KMIP 1.3
    OffsetItems(KMIP_1_3, 0x4200D4),
    LocatedItems(KMIP_1_3, 0x4200D5),
    CorrelationValue(KMIP_1_3, 0x4200D6),
    InitIndicator(KMIP_1_3, 0x4200D7),
    FinalIndicator(KMIP_1_3, 0x4200D8),
    RNGParameters(KMIP_1_3, 0x4200D9),
    RNGAlgorithm(KMIP_1_3, 0x4200DA),
    DRBGAlgorithm(KMIP_1_3, 0x4200DB),
    FIPS186Variation(KMIP_1_3, 0x4200DC),
    PredictionResistance(KMIP_1_3, 0x4200DD),
    RandomNumberGenerator(KMIP_1_3, 0x4200DE),
    ValidationInformation(KMIP_1_3, 0x4200DF),
    ValidationAuthorityType(KMIP_1_3, 0x4200E0),
    ValidationAuthorityCountry(KMIP_1_3, 0x4200E1),
    ValidationAuthorityURI(KMIP_1_3, 0x4200E2),
    ValidationVersionMajor(KMIP_1_3, 0x4200E3),
    ValidationVersionMinor(KMIP_1_3, 0x4200E4),
    ValidationType(KMIP_1_3, 0x4200E5),
    ValidationLevel(KMIP_1_3, 0x4200E6),
    ValidationCertificateIdentifier(KMIP_1_3, 0x4200E7),
    ValidationCertificateURI(KMIP_1_3, 0x4200E8),
    ValidationVendorURI(KMIP_1_3, 0x4200E9),
    ValidationProfile(KMIP_1_3, 0x4200EA),
    ProfileInformation(KMIP_1_3, 0x4200EB),
    ProfileName(KMIP_1_3, 0x4200EC),
    ServerURI(KMIP_1_3, 0x4200ED),
    ServerPort(KMIP_1_3, 0x4200EE),
    StreamingCapability(KMIP_1_3, 0x4200EF),
    AsynchronousCapability(KMIP_1_3, 0x4200F0),
    AttestationCapability(KMIP_1_3, 0x4200F1),
    UnwrapMode(KMIP_1_3, 0x4200F2),
    DestroyAction(KMIP_1_3, 0x4200F3),
    ShreddingAlgorithm(KMIP_1_3, 0x4200F4),
    RNGMode(KMIP_1_3, 0x4200F5),
    ClientRegistrationMethod(KMIP_1_3, 0x4200F6),
    CapabilityInformation(KMIP_1_3, 0x4200F7),

    // Extensions: 0x540000 – 0x54FFFF
    NonStandardExtensionTag(KMIP_1_0, 0x540000);

    private final java.lang.Integer value;
    private final Version introducedInVersion;
    private final Version deprecatedInVersion;

    @Override
    public Integer getValue() {
        return value;
    }

    @Override
    public Version getIntroducedInVersion() {
        return introducedInVersion;
    }

    @Override
    public Version getDeprecatedInVersion() {
        return deprecatedInVersion;
    }

    @Override
    public boolean isValidInVersion(final Version version) {
        if (version == null) {
            throw new NullPointerException("No version parameter specified!");
        }

        final Version deprecatedIn = getDeprecatedInVersion();
        return getIntroducedInVersion().compareTo(version) <= 0 &&
                (deprecatedIn == null || deprecatedIn.compareTo(version) >= 0);
    }

    @Override
    public String toString() {
        return String.format("%s(0x%H)", name(), getValue());
    }

    public static StandardTag valueOf(final Integer value) {
        if (value == null) {
            throw new NullPointerException("No value specified!");
        }

        // Extensions: 0x540000 – 0x54FFFF
        if (value >= 0x540000 && value <= 0x54FFFF) {
            return NonStandardExtensionTag;
        }

        // Standard Tag value
        final StandardTag result = valueMap.get(value);
        if (result == null) {
            throw new IllegalArgumentException(String.format("Unrecognized Tag value: 0x%H", value));
        }
        return result;
    }

    private static final Map<Integer, StandardTag> valueMap = new HashMap<>(300);

    StandardTag(Version introducedInVersion, java.lang.Integer value) {
        this(introducedInVersion, value, null);
    }

    StandardTag(Version introducedInVersion, java.lang.Integer value, Version deprecatedInVersion) {
        this.value = value;
        this.introducedInVersion = introducedInVersion;
        this.deprecatedInVersion = deprecatedInVersion;
    }

    static {
        for (StandardTag tag : StandardTag.values()) {
            if (NonStandardExtensionTag == tag) {
                continue;
            }
            valueMap.put(tag.value, tag);
        }
    }
}

