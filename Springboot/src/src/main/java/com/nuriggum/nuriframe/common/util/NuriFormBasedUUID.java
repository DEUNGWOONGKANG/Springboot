package com.nuriggum.nuriframe.common.util;

import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 *
 * A class that represents an immutable universally unique identifier (UUID). A
 * UUID represents a 128-bit value.
 *
 * <p>
 * There exist different variants of these global identifiers. The methods of
 * this class are for manipulating the Leach-Salz variant, although the
 * constructors allow the creation of any variant of UUID (described below).
 *
 * <p>
 * The layout of a variant 2 (Leach-Salz) UUID is as follows:
 *
 * The most significant long consists of the following unsigned fields:
 *
 * <pre>
 *   0xFFFFFFFF00000000 time_low
 *   0x00000000FFFF0000 time_mid
 *   0x000000000000F000 version
 *   0x0000000000000FFF time_hi
 * </pre>
 *
 * The least significant long consists of the following unsigned fields:
 *
 * <pre>
 *   0xC000000000000000 variant
 *   0x3FFF000000000000 clock_seq
 *   0x0000FFFFFFFFFFFF node
 * </pre>
 *
 * <p>
 * The variant field contains a value which identifies the layout of the
 * <tt>UUID</tt>. The bit layout described above is valid only for a
 * <tt>UUID</tt> with a variant value of 2, which indicates the Leach-Salz
 * variant.
 *
 * <p>
 * The version field holds a value that describes the type of this <tt>UUID</tt>.
 * There are four different basic types of UUIDs: time-based, DCE security,
 * name-based, and randomly generated UUIDs. These types have a version value of
 * 1, 2, 3 and 4, respectively.
 *
 * <p>
 * For more information including algorithms used to create <tt>UUID</tt>s,
 * see the Internet-Draft <a
 * href="http://www.ietf.org/internet-drafts/draft-mealling-uuid-urn-03.txt">UUIDs
 * and GUIDs</a> or the standards body definition at <a
 * href="http://www.iso.ch/cate/d2229.html">ISO/IEC 11578:1996</a>.
 *
 * @version 1.14, 07/12/04
 * @since 1.5
 */
@SuppressWarnings("serial")
public class NuriFormBasedUUID implements Serializable {
    /*
     * The most significant 64 bits of this UUID.
     *
     * @serial
     */
    private final long mostSigBits;

    /*
     * The least significant 64 bits of this UUID.
     *
     * @serial
     */
    private final long leastSigBits;

    /*
     * The version number associated with this UUID. Computed on demand.
     */
    private transient int version = -1;

    /*
     * The variant number associated with this UUID. Computed on demand.
     */
    private transient int variant = -1;

    /*
     * The timestamp associated with this UUID. Computed on demand.
     */
    private transient volatile long timestamp = -1;

    /*
     * The clock sequence associated with this UUID. Computed on demand.
     */
    private transient int sequence = -1;

    /*
     * The node number associated with this UUID. Computed on demand.
     */
    private transient long node = -1;

    /*
     * The hashcode of this UUID. Computed on demand.
     */
    private transient int hashCode = -1;

    /*
     * The random number generator used by this class to create random based
     * UUIDs.
     */
    private static volatile SecureRandom numberGenerator = null;
    
    private static SecureRandom makeSecureRandom() {
    	SecureRandom ng = numberGenerator;
        if (ng == null) {
            numberGenerator = ng = new SecureRandom();
        }
        return ng;
    }

    // Constructors and Factories

    /*
     * Private constructor which uses a byte array to construct the new UUID.
     */
    private NuriFormBasedUUID(byte[] data) {
        long msb = 0;
        long lsb = 0;
        for (int i = 0; i < 8; i++)
            msb = (msb << 8) | (data[i] & 0xff);
        for (int i = 8; i < 16; i++)
            lsb = (lsb << 8) | (data[i] & 0xff);
        this.mostSigBits = msb;
        this.leastSigBits = lsb;
    }

    /**
     * Constructs a new <tt>UUID</tt> using the specified data.
     * <tt>mostSigBits</tt> is used for the most significant 64 bits of the
     * <tt>UUID</tt> and <tt>leastSigBits</tt> becomes the least significant
     * 64 bits of the <tt>UUID</tt>.
     *
     * @param mostSigBits
     * @param leastSigBits
     */
    public NuriFormBasedUUID(long mostSigBits, long leastSigBits) {
        this.mostSigBits = mostSigBits;
        this.leastSigBits = leastSigBits;
    }

    /**
     * Static factory to retrieve a type 4 (pseudo randomly generated) UUID.
     *
     * The <code>UUID</code> is generated using a cryptographically strong
     * pseudo random number generator.
     *
     * @return a randomly generated <tt>UUID</tt>.
     */
    public static NuriFormBasedUUID randomUUID() {
    	SecureRandom ng = makeSecureRandom();

        byte[] randomBytes = new byte[16];
        ng.nextBytes(randomBytes);
        randomBytes[6] &= 0x0f; /* clear version */
        randomBytes[6] |= 0x40; /* set to version 4 */
        randomBytes[8] &= 0x3f; /* clear variant */
        randomBytes[8] |= 0x80; /* set to IETF variant */

        return new NuriFormBasedUUID(randomBytes);
    }

    /**
     * Static factory to retrieve a type 3 (name based) <tt>UUID</tt> based on
     * the specified byte array.
     *
     * @param name
     *            a byte array to be used to construct a <tt>UUID</tt>.
     * @return a <tt>UUID</tt> generated from the specified array.
     */
    public static NuriFormBasedUUID nameUUIDFromBytes(byte[] name) {
        MessageDigest md;
        try {
        	// 2011.10.10 보안점검 후속조치 암호화 알고리즘 변경(MD5 -> SHA-256)
        	//md = MessageDigest.getInstance("MD5");
            md = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException nsae) {
        	//throw new InternalError("MD5 not supported");
            throw new InternalError("SHA-256 not supported");
        }
        // 2011.10.10 보안점검 후속조치
        if (md == null) {
            throw new RuntimeException("MessageDigest is null!!");
        }
        // 2014.09.20 보안점검 후속 조치
        // Random 방식의 salt 추가
        SecureRandom ng = makeSecureRandom();
        byte[] randomBytes = new byte[16];
        ng.nextBytes(randomBytes);

        md.reset();
        md.update(randomBytes);
        byte[] sha = md.digest(name);


        byte[] md5Bytes = new byte[9];
        System.arraycopy(sha, 0, md5Bytes, 0, 8);
        //2011.10.10 보안점검 후속조치 끝

        md5Bytes[6] &= 0x0f; /* 버전 비트 초기화 */
        md5Bytes[6] |= 0x30; /* 버전 3으로 설정 */
        md5Bytes[7] &= 0x3f; /* 변형 비트 초기화 */
        md5Bytes[8] |= 0x80;

        return new NuriFormBasedUUID(md5Bytes);
    }

    // Field Accessor Methods

    /**
     * Returns the least significant 64 bits of this UUID's 128 bit value.
     *
     * @return the least significant 64 bits of this UUID's 128 bit value.
     */
    public long getLeastSignificantBits() {
        return leastSigBits;
    }

    /**
     * Returns the most significant 64 bits of this UUID's 128 bit value.
     *
     * @return the most significant 64 bits of this UUID's 128 bit value.
     */
    public long getMostSignificantBits() {
        return mostSigBits;
    }

    /**
     * The version number associated with this <tt>UUID</tt>. The version
     * number describes how this <tt>UUID</tt> was generated.
     *
     * The version number has the following meaning:
     * <p>
     * <ul>
     * <li>1 Time-based UUID
     * <li>2 DCE security UUID
     * <li>3 Name-based UUID
     * <li>4 Randomly generated UUID
     * </ul>
     *
     * @return the version number of this <tt>UUID</tt>.
     */
    public int version() {
        if (version < 0) {
            // Version is bits masked by 0x000000000000F000 in MS long
            version = (int) ((mostSigBits >> 12) & 0x0f);
        }
        return version;
    }

    /**
     * The variant number associated with this <tt>UUID</tt>. The variant
     * number describes the layout of the <tt>UUID</tt>.
     *
     * The variant number has the following meaning:
     * <p>
     * <ul>
     * <li>0 Reserved for NCS backward compatibility
     * <li>2 The Leach-Salz variant (used by this class)
     * <li>6 Reserved, Microsoft Corporation backward compatibility
     * <li>7 Reserved for future definition
     * </ul>
     *
     * @return the variant number of this <tt>UUID</tt>.
     */
    public int variant() {
        if (variant < 0) {
            // This field is composed of a varying number of bits
            if ((leastSigBits >>> 63) == 0) {
                variant = 0;
            } else if ((leastSigBits >>> 62) == 2) {
                variant = 2;
            } else {
                variant = (int) (leastSigBits >>> 61);
            }
        }
        return variant;
    }

    // Object Inherited Methods

    /**
     * Returns a <code>String</code> object representing this
     * <code>UUID</code>.
     *
     * <p>
     * The UUID string representation is as described by this BNF :
     *
     * <pre>
     *    UUID                   = &lt;time_low&gt; &quot;-&quot; &lt;time_mid&gt; &quot;-&quot;
     *                             &lt;time_high_and_version&gt; &quot;-&quot;
     *                             &lt;variant_and_sequence&gt; &quot;-&quot;
     *                             &lt;node&gt;
     *    time_low               = 4*&lt;hexOctet&gt;
     *    time_mid               = 2*&lt;hexOctet&gt;
     *    time_high_and_version  = 2*&lt;hexOctet&gt;
     *    variant_and_sequence   = 2*&lt;hexOctet&gt;
     *    node                   = 6*&lt;hexOctet&gt;
     *    hexOctet               = &lt;hexDigit&gt;&lt;hexDigit&gt;
     *    hexDigit               =
     *          &quot;0&quot; | &quot;1&quot; | &quot;2&quot; | &quot;3&quot; | &quot;4&quot; | &quot;5&quot; | &quot;6&quot; | &quot;7&quot; | &quot;8&quot; | &quot;9&quot;
     *          | &quot;a&quot; | &quot;b&quot; | &quot;c&quot; | &quot;d&quot; | &quot;e&quot; | &quot;f&quot;
     *          | &quot;A&quot; | &quot;B&quot; | &quot;C&quot; | &quot;D&quot; | &quot;E&quot; | &quot;F&quot;
     * </pre>
     *
     * @return a string representation of this <tt>UUID</tt>.
     */
    @Override
	public String toString() {
        return (digits(mostSigBits >> 32, 8) + "-"
                + digits(mostSigBits >> 16, 4) + "-" + digits(mostSigBits, 4)
                + "-" + digits(leastSigBits >> 48, 4) + "-" + digits(
                leastSigBits, 12));
    }

    /** Returns val represented by the specified number of hex digits. */
    private static String digits(long val, int digits) {
        long hi = 1L << (digits * 4);
        return Long.toHexString(hi | (val & (hi - 1))).substring(1);
    }

    /**
     * Returns a hash code for this <code>UUID</code>.
     *
     * @return a hash code value for this <tt>UUID</tt>.
     */
    @Override
	public int hashCode() {
        if (hashCode == -1) {
            hashCode = (int) ((mostSigBits >> 32) ^ mostSigBits
                    ^ (leastSigBits >> 32) ^ leastSigBits);
        }
        return hashCode;
    }

    /**
     * Compares this object to the specified object. The result is <tt>true</tt>
     * if and only if the argument is not <tt>null</tt>, is a <tt>UUID</tt>
     * object, has the same variant, and contains the same value, bit for bit,
     * as this <tt>UUID</tt>.
     *
     * @param obj
     *            the object to compare with.
     * @return <code>true</code> if the objects are the same;
     *         <code>false</code> otherwise.
     */
    @Override
	public boolean equals(Object obj) {
        // 보안 취약점 점검 지적사항 반영 시작
    	if (obj == null)
        	return false;
    	// 보안 취약점 점검 지적사항 반영 시작 끝
    	if (!(obj instanceof NuriFormBasedUUID))
            return false;
        if (((NuriFormBasedUUID) obj).variant() != this.variant())
            return false;
        NuriFormBasedUUID id = (NuriFormBasedUUID) obj;
        return (mostSigBits == id.mostSigBits && leastSigBits == id.leastSigBits);
    }

    /**
     * Reconstitute the <tt>UUID</tt> instance from a stream (that is,
     * deserialize it). This is necessary to set the transient fields to their
     * correct uninitialized value so they will be recomputed on demand.
     */
    private void readObject(java.io.ObjectInputStream in)
            throws java.io.IOException, ClassNotFoundException {

        in.defaultReadObject();

        // Set "cached computation" fields to their initial values
        version = -1;
        variant = -1;
        timestamp = -1;
        sequence = -1;
        node = -1;
        hashCode = -1;
    }

    public static String createUUID(){
        return randomUUID().toString().replaceAll("-", "").toUpperCase();
    }
}